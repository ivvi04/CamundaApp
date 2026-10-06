package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lakeevda.camundaapp.application.dto.TicketStartProcessUseCaseResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.out.process.CamundaProcess;
import ru.lakeevda.camundaapp.presentation.dto.TicketGetControllerResponse;
import ru.lakeevda.camundaapp.presentation.dto.TicketStartProcessControllerRequest;
import ru.lakeevda.camundaapp.presentation.dto.TicketStartProcessControllerResponse;
import ru.lakeevda.camundaapp.presentation.mapper.TicketMapper;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketUseCase useCase;
    private final CamundaProcess camundaProcess;

    @GetMapping
    public List<TicketGetControllerResponse> getByUserId(@Valid @RequestParam Long userId) {
        return useCase.getByUserId(userId).stream()
                .map(TicketMapper::toGetResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TicketStartProcessControllerResponse> start(@RequestBody TicketStartProcessControllerRequest ticketRequest) {
        TicketStartProcessUseCaseResponse paramResponse = useCase.startProcess(ticketRequest.userId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TicketMapper.toStartProcessResponse(paramResponse));
    }
}
