package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.domain.entity.ticket.TicketStatus;
import ru.lakeevda.camundaapp.presentation.dto.TicketRequest;
import ru.lakeevda.camundaapp.presentation.dto.TicketResponse;
import ru.lakeevda.camundaapp.presentation.mapper.TicketMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketUseCase useCase;

    @GetMapping
    public List<TicketResponse> getByUserId(@Valid @RequestParam Long userId) {
        return useCase.getByUserId(userId).stream()
                .map(TicketMapper::fromParam)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody TicketRequest ticketRequest) {
        TicketParamRequest paramRequest = new TicketParamRequest(
                ticketRequest.name(),
                LocalDateTime.now(),
                TicketStatus.CREATED.getValue(),
                ticketRequest.userId());
        TicketParamResponse paramResponse = useCase.create(paramRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TicketMapper.fromParam(paramResponse));
    }
}
