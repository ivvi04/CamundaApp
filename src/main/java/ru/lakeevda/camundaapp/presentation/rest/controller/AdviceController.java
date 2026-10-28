package ru.lakeevda.camundaapp.presentation.rest.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.lakeevda.camundaapp.application.exception.NotFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgument(IllegalArgumentException e) {
        log.warn("Некорректный запрос: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({HandlerMethodValidationException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodValidation(RuntimeException e) {
        log.warn("Ошибка валидации параметров: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Ошибка валидации тела запроса: {}", message);
        return message;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingParameter(MissingServletRequestParameterException e) {
        log.warn("Отсутствует обязательный параметр: {}", e.getParameterName());
        return "Параметр " + e.getParameterName() + " обязателен";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String messageNotReadable(HttpMessageNotReadableException e) {
        log.warn("Не удалось разобрать тело запроса: {}", e.getMessage());
        return "Некорректный формат запроса";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(NotFoundException e) {
        log.warn("Сущность не найдена: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String dataIntegrityViolation(DataIntegrityViolationException e) {
        log.warn("Нарушение целостности данных: {}", e.getMostSpecificCause().getMessage());
        return "Нарушение целостности данных: запись с такими уникальными полями уже существует";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String otherException(RuntimeException e) {
        log.error("Необработанная ошибка", e);
        return "Неизвестная ошибка: " + e.getMessage();
    }
}
