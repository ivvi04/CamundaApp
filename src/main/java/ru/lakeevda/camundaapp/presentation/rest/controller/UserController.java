package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lakeevda.camundaapp.application.dto.UserParamResponse;
import ru.lakeevda.camundaapp.application.port.in.usecase.UserUseCase;
import ru.lakeevda.camundaapp.presentation.dto.UserRequest;
import ru.lakeevda.camundaapp.presentation.dto.UserResponse;
import ru.lakeevda.camundaapp.presentation.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase useCase;

    @GetMapping
    public UserResponse getByEmail(@Valid @RequestParam String email) {
        UserParamResponse paramResponse = useCase.getByEmail(email);
        return UserMapper.fromParam(paramResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        UserParamResponse paramResponse = useCase.create(UserMapper.toParam(userRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.fromParam(paramResponse));
    }
}
