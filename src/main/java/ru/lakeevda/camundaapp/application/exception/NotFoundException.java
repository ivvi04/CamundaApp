package ru.lakeevda.camundaapp.application.exception;

/**
 * Исключение прикладного слоя: запрошенная сущность не найдена.
 * Не зависит от JPA и других инфраструктурных библиотек.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
