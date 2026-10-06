package ru.lakeevda.camundaapp.application.port.out.process;

import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartRequest;
import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartResponse;

/**
 * Порт для запуска Camunda BPMN-процессов.
 */
public interface ProcessService {

    /**
     * Запускает процесс.
     *
     * @param camundaStartProcessRequest ID BPMN-процесса
     * @param variables переменные
     * @return переменные processInstanceKey, bpmnProcessId, version
     */
    ProcessServiceStartResponse startProcess(ProcessServiceStartRequest camundaStartProcessRequest);
}
