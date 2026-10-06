package ru.lakeevda.camundaapp.presentation.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartRequest;
import ru.lakeevda.camundaapp.application.dto.ProcessServiceStartResponse;
import ru.lakeevda.camundaapp.application.port.out.process.ProcessService;
import ru.lakeevda.camundaapp.presentation.dto.ProcessControllerStartRequest;
import ru.lakeevda.camundaapp.presentation.dto.ProcessControllerStartResponse;
import ru.lakeevda.camundaapp.presentation.mapper.TicketMapper;

@RestController("api/process")
@RequiredArgsConstructor
public class CamundaController {

    private final ProcessService processService;

    @PostMapping("/start")
    public ResponseEntity<ProcessControllerStartResponse> start(@RequestBody ProcessControllerStartRequest request) {
        ProcessServiceStartResponse paramResponse = processService
                .startProcess(new ProcessServiceStartRequest(request.processId(), request.variables()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TicketMapper.toStartProcessResponse(paramResponse));
    }
}
