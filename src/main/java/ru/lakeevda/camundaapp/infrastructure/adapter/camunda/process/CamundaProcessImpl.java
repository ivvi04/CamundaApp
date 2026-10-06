package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.process;

import io.camunda.client.CamundaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.port.out.process.CamundaProcess;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaProcessImpl implements CamundaProcess {

    private final CamundaClient camundaClient;

    @Override
    public Map<String, Object> startProcess(String processId, Map<String, Object> variables) {
        log.info("Starting createTicketProcess: variables={}", variables);

        // Запуск экземпляра процесса
        var response = camundaClient
                .newCreateInstanceCommand()
                .bpmnProcessId(processId)   // ID из BPMN (атрибут id у <process>)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        return Map.of(
                "processInstanceKey", response.getProcessInstanceKey(),
                "bpmnProcessId", response.getBpmnProcessId(),
                "version", response.getVersion()
        );
    }
}
