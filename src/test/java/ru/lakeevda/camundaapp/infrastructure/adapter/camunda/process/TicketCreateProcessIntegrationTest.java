package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.process;

import io.camunda.client.CamundaClient;
import io.camunda.process.test.api.CamundaAssert;
import io.camunda.process.test.api.CamundaProcessTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lakeevda.camundaapp.infrastructure.adapter.camunda.CamundaProcessBaseIntegrationTest;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.TicketEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.entity.UserEntity;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.TicketJpaRepository;
import ru.lakeevda.camundaapp.infrastructure.persistence.repository.UserJpaRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TicketCreateProcessIntegrationTest extends CamundaProcessBaseIntegrationTest {

    @Autowired
    private CamundaClient camundaClient;

    @Autowired
    private CamundaProcessTestContext processTestContext;

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private TicketJpaRepository ticketRepository;

    private String userId;


    @BeforeEach
    void setUp() {
        ticketRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        camundaClient.newDeployResourceCommand()
                .addResourceFromClasspath("processes/bpmn/createTicketProcess.bpmn")
                .addResourceFromClasspath("processes/form/ticketInputParam.form")
                .send()
                .join();

        UserEntity user = new UserEntity();
        user.setFio("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setEmail("test@example.com");
        user = userRepository.saveAndFlush(user);
        userId = String.valueOf(user.getId());
    }

    @Test
    void shouldCompleteHappyPath() {
        var processInstance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId("ticketCreateProcess")
                .latestVersion()
                .variables(Map.of("timeoutDuration", "PT1M"))
                .send()
                .join();

        processTestContext.completeUserTask(
                "ticketInputParamTask",
                Map.of("ticketName", "Test Ticket", "userId", userId)
        );

        CamundaAssert.assertThat(processInstance).isCompleted();
        CamundaAssert.assertThat(processInstance)
                .hasCompletedElementsInOrder("startEvent", "ticketInputParamTask", "ticketCreateTask", "ticketCheckTask", "endEvent");

        var tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(1);
        var ticket = tickets.getFirst();
        assertThat(ticket.getName()).isEqualTo("Test Ticket");
        assertThat(ticket.getStatus()).isEqualTo("created");
        assertThat(ticket.getUser().getId()).isEqualTo(Long.valueOf(userId));
    }

    @Test
    void shouldHandleBusinessErrorAndRetry() {
        var processInstance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId("ticketCreateProcess")
                .latestVersion()
                .variables(Map.of("timeoutDuration", "PT1M"))
                .send()
                .join();

        // Первая попытка: несуществующий userId → BUSINESS_ERROR → возврат к user task
        processTestContext.completeUserTask(
                "ticketInputParamTask",
                Map.of("ticketName", "Test Ticket", "userId", "9999")
        );

        // Вторая попытка: валидный userId
        processTestContext.completeUserTask(
                "ticketInputParamTask",
                Map.of("ticketName", "Test Ticket Retry", "userId", userId)
        );

        CamundaAssert.assertThat(processInstance).isCompleted();
        CamundaAssert.assertThat(processInstance)
                .hasCompletedElementsInOrder(
                        "startEvent",
                        "ticketInputParamTask",
                        "ticketInputParamTask",
                        "ticketCreateTask",
                        "ticketCheckTask",
                        "endEvent"
                );

        var tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(1);
        assertThat(tickets.getFirst().getName()).isEqualTo("Test Ticket Retry");
    }

    @Test
    void shouldHandleCheckTimeout() throws Exception {
        // Создаём dummy ticket (id=1), чтобы следующий получил чётный id=2
        TicketEntity dummyTicket = new TicketEntity();
        dummyTicket.setName("dummy");
        dummyTicket.setCreateAt(LocalDateTime.now());
        dummyTicket.setStatus("created");
        dummyTicket.setUser(userRepository.findById(Long.valueOf(userId)).orElseThrow());
        ticketRepository.saveAndFlush(dummyTicket);

        var processInstance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId("ticketCreateProcess")
                .latestVersion()
                .variables(Map.of("timeoutDuration", "PT1S"))
                .send()
                .join();

        processTestContext.completeUserTask(
                "ticketInputParamTask",
                Map.of("ticketName", "Timeout Ticket", "userId", userId)
        );

        // Ждём, пока ticketCreateTask завершится — процесс перейдёт к ticketCheckTask,
        // и timer boundary event начнёт отсчёт
        CamundaAssert.assertThat(processInstance).hasCompletedElements("ticketCreateTask");

        // Перематываем часы — timer boundary event (PT1S) срабатывает
        processTestContext.increaseTime(Duration.ofSeconds(2));

        // Ждём, пока timer boundary event сработает и процесс дойдёт до timeOutEndEvent
        CamundaAssert.assertThat(processInstance).hasActiveElements("timeOutEndEvent");
        CamundaAssert.assertThat(processInstance)
                .hasCompletedElementsInOrder("startEvent", "ticketInputParamTask", "ticketCreateTask");
    }

}
