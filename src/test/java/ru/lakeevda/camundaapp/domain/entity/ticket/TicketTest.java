package ru.lakeevda.camundaapp.domain.entity.ticket;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ru.lakeevda.camundaapp.domain.entity.user.User;
import ru.lakeevda.camundaapp.domain.entity.user.UserId;
import ru.lakeevda.camundaapp.domain.entity.user.UserFio;
import ru.lakeevda.camundaapp.domain.entity.user.UserBirthday;
import ru.lakeevda.camundaapp.domain.entity.user.UserEmail;

public class TicketTest {

    private static final User SAMPLE_USER = createSampleUser();

    // ---- helper ----

    private static User createSampleUser() {
        return User.restore(
            UserId.of(1L),
            UserFio.of("Ivan Ivanov"),
            UserBirthday.of(java.time.LocalDate.of(1990, 1, 1)),
            UserEmail.of("ivan@example.com")
        );
    }

    // ---- create() — успешные сценарии ----

    @Test
    void create_success() {
        Ticket ticket = Ticket.create(TicketName.of("Support request"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER);

        assertNotNull(ticket);
        assertNull(ticket.getId()); // без ID в create()
    }

    @Test
    void create_nameIsNotNull() {
        Ticket ticket = Ticket.create(TicketName.of("Bug fix"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.RESOLVED, SAMPLE_USER);
        assertEquals("Bug fix", ticket.getName().getValue());
    }

    @Test
    void create_statusIsNotNull() {
        Ticket ticket = Ticket.create(TicketName.of("Feature"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CLOSED, SAMPLE_USER);
        assertEquals(TicketStatus.CLOSED, ticket.getStatus());
    }

    @Test
    void create_userIsNotNull() {
        Ticket ticket = Ticket.create(TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER);
        assertNotNull(ticket.getUser());
        assertEquals("Ivan Ivanov", ticket.getUser().getFio().getValue());
    }

    @Test
    void create_createAtIsNotNull() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        Ticket ticket = Ticket.create(TicketName.of("Task"), TicketCreateAt.of(now), TicketStatus.CREATED, SAMPLE_USER);
        assertEquals(now, ticket.getCreateAt().getValue());
    }

    // ---- restore() — успешные сценарии ----

    @Test
    void restore_success() {
        Ticket ticket = Ticket.restore(TicketId.of(100L), TicketName.of("Support request"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER);

        assertNotNull(ticket);
        assertEquals(100L, ticket.getId().getValue()); // ID установлен в restore()
    }

    @Test
    void restore_idIsNotNull() {
        Ticket ticket = Ticket.restore(TicketId.of(250L), TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.RESOLVED, SAMPLE_USER);
        assertEquals(250L, ticket.getId().getValue());
    }

    @Test
    void restore_nameIsNotNull() {
        Ticket ticket = Ticket.restore(TicketId.of(10L), TicketName.of("Bug fix"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER);
        assertEquals("Bug fix", ticket.getName().getValue());
    }

    @Test
    void restore_statusIsNotNull() {
        Ticket ticket = Ticket.restore(TicketId.of(10L), TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CLOSED, SAMPLE_USER);
        assertEquals(TicketStatus.CLOSED, ticket.getStatus());
    }

    @Test
    void restore_userIsNotNull() {
        Ticket ticket = Ticket.restore(TicketId.of(10L), TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER);
        assertNotNull(ticket.getUser());
        assertEquals("Ivan Ivanov", ticket.getUser().getFio().getValue());
    }

    @Test
    void restore_createAtIsNotNull() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        Ticket ticket = Ticket.restore(TicketId.of(10L), TicketName.of("Task"), TicketCreateAt.of(now), TicketStatus.CREATED, SAMPLE_USER);
        assertEquals(now, ticket.getCreateAt().getValue());
    }

    // ---- restore() — отрицательные сценарии (null-проверки) ----

    @Test
    void restore_throwsWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Ticket.restore(null, TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER));
    }

    @Test
    void restore_throwsWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Ticket.restore(TicketId.of(1L), null, TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, SAMPLE_USER));
    }

    @Test
    void restore_throwsWhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Ticket.restore(TicketId.of(1L), TicketName.of("Task"), TicketCreateAt.of(java.time.LocalDateTime.now()), TicketStatus.CREATED, null));
    }
}
