package ru.lakeevda.camundaapp.infrastructure.adapter.camunda.process;

import io.camunda.client.CamundaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartRequest;
import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartResponse;
import ru.lakeevda.camundaapp.application.port.out.process.ProcessService;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaProcessServiceImpl implements ProcessService {

    private final Map<String, Object> MAIN_VARIABLES = Map.of(
            "timeoutDuration", "PT1M"
    );

    private final CamundaClient camundaClient;

    @Override
    public ProcessServiceStartResponse startProcess(ProcessServiceStartRequest startRequest) {
        log.info("Starting createTicketProcess: processId={}, variables={}", startRequest.processId(), startRequest.variables());

        var variables = startRequest.variables();
        variables.putAll(MAIN_VARIABLES);

        // Запуск экземпляра процесса
        var response = camundaClient
                .newCreateInstanceCommand()
                .bpmnProcessId(startRequest.processId())   // ID из BPMN (атрибут id у <process>)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        return new ProcessServiceStartResponse(
                String.valueOf(response.getProcessInstanceKey()),
                response.getBpmnProcessId(),
                String.valueOf(response.getVersion()));
    }
}
