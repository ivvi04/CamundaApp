package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.presentation.dto.TicketControllerGetResponse;
import ru.lakeevda.camundaapp.presentation.mapper.TicketMapper;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketUseCase useCase;

    @GetMapping
    public List<TicketControllerGetResponse> getByUserId(@RequestParam @NotNull @Min(value = 1, message = "userId must be positive") Long userId) {
        return useCase.getByUserId(userId).stream()
                .map(TicketMapper::toGetResponse)
                .toList();
    }
}
