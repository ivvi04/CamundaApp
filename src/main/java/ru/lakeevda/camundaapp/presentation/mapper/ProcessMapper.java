package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartResponse;
import ru.lakeevda.camundaapp.presentation.dto.ProcessControllerStartResponse;

public class ProcessMapper {

    public static ProcessControllerStartResponse toStartProcessResponse(ProcessServiceStartResponse paramResponse) {
        return new ProcessControllerStartResponse(
                paramResponse.processInstanceKey(), paramResponse.bpmnProcessId(), paramResponse.version());
    }
}
