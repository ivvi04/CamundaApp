package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketGetUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketStartProcessUseCaseResponse;
import ru.lakeevda.camundaapp.presentation.dto.TicketGetControllerResponse;
import ru.lakeevda.camundaapp.presentation.dto.TicketStartProcessControllerResponse;

public class TicketMapper {
    public static TicketGetControllerResponse toGetResponse(TicketGetUseCaseResponse paramResponse) {
        return new TicketGetControllerResponse(
                paramResponse.id(), paramResponse.name(), paramResponse.createAt(), paramResponse.status());
    }

    public static TicketStartProcessControllerResponse toStartProcessResponse(TicketStartProcessUseCaseResponse paramResponse) {
        return new TicketStartProcessControllerResponse(
                paramResponse.processInstanceKey(), paramResponse.bpmnProcessId(), paramResponse.version());
    }
}
