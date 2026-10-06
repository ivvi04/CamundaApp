package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.UserUseCaseGetResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerCreateRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerCreateResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserControllerGetResponse;
import ru.lakeevda.camundaapp.presentation.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase useCase;

    @GetMapping
    public UserControllerGetResponse getByEmail(@RequestParam @NotBlank(message = "email cannot be empty") String email) {
        UserUseCaseGetResponse paramResponse = useCase.getByEmail(email);
        return UserMapper.toGetResponse(paramResponse);
    }

    @PostMapping
    public ResponseEntity<UserControllerCreateResponse> create(@Valid @RequestBody UserControllerCreateRequest userRequest) {
        UserUseCaseCreateResponse paramResponse = useCase.create(UserMapper.toCreateRequest(userRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toCreateResponse(paramResponse));
    }
}
