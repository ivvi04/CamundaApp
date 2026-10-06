package ru.lakeevda.camundaapp.application.port.out.process;

import java.util.Map;

/**
 * Порт для запуска Camunda BPMN-процессов.
 */
public interface CamundaProcess {

    /**
     * Запускает процесс.
     *
     * @param processId ID BPMN-процесса
     * @param variables переменные
     * @return переменные processInstanceKey, bpmnProcessId, version
     */
    Map<String, Object> startProcess(String processId, Map<String, Object> variables);
}
