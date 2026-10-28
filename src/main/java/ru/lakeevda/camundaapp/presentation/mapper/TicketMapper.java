package ru.lakeevda.camundaapp.presentation.mapper;

import ru.lakeevda.camundaapp.application.dto.TicketUseCaseGetResponse;
import ru.lakeevda.camundaapp.presentation.dto.TicketControllerGetResponse;

public class TicketMapper {
    public static TicketControllerGetResponse toGetResponse(TicketUseCaseGetResponse paramResponse) {
        return new TicketControllerGetResponse(
                paramResponse.id(), paramResponse.name(), paramResponse.createAt(), paramResponse.status());
    }
}
