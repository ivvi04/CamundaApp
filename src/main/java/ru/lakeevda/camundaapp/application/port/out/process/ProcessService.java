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
     * @param camundaStartProcessRequest входные параметры старта BPMN-процесса
     * @return выходные параметры старта BPMN-процесса
     */
    ProcessServiceStartResponse startProcess(ProcessServiceStartRequest camundaStartProcessRequest);
}
