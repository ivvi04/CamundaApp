package ru.lakeevda.camundaapp.domain.entity.ticket;

/**
 * Value object representing a name.
 */
public final class TicketName {
    private final String value;

    private TicketName(String value) {
        this.value = value;
    }

    public static TicketName of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        return new TicketName(value.trim());
    }

    public String getValue() {
        return this.value;
    }
}
