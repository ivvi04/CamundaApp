package ru.lakeevda.camundaapp.domain.entity.ticket;

import java.util.Arrays;

public enum TicketStatus {
    CREATED("created", "Создан"),
    RESOLVED("resolved", "Выполнен"),
    CLOSED("closed", "Закрыт");

    private final String value;
    private final String description;

    TicketStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static TicketStatus fromValue(String value) {
        return Arrays.stream(TicketStatus.values())
                .filter(t -> t.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Value not found: " + value));
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }
}
