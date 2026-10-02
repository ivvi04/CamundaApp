package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.presentation.dto.TicketResponse;

public class TicketMapper {
    public static TicketResponse fromParam(TicketParamResponse paramResponse) {
        return new TicketResponse(paramResponse.id(), paramResponse.name(), paramResponse.createAt(), paramResponse.status());
    }
}
