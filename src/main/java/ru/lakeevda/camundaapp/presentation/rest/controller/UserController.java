package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lakeevda.camundaapp.application.dto.UserCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.UserGetUseCaseResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.presentation.dto.UserCreateControllerRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserCreateControllerResponse;
import ru.lakeevda.camundaapp.presentation.dto.UserGetControllerResponse;
import ru.lakeevda.camundaapp.presentation.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase useCase;

    @GetMapping
    public UserGetControllerResponse getByEmail(@Valid @RequestParam String email) {
        UserGetUseCaseResponse paramResponse = useCase.getByEmail(email);
        return UserMapper.toGetResponse(paramResponse);
    }

    @PostMapping
    public ResponseEntity<UserCreateControllerResponse> create(@RequestBody UserCreateControllerRequest userRequest) {
        UserCreateUseCaseResponse paramResponse = useCase.create(UserMapper.toCreateRequest(userRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toCreateResponse(paramResponse));
    }
}
