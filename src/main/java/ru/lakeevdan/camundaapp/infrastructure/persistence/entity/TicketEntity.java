package ru.lakeevdan.camundaapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Сущность тикета (таблица camunda_app.tickets).
 */
@Entity
@Table(name = "tickets", schema = "camunda_app")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название тикета. */
    @Column(nullable = false, columnDefinition = "text")
    private String name;

    /** Дата и время создания. */
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    /** Статус тикета. */
    @Column(nullable = false, columnDefinition = "varchar")
    private String status;

    /** Пользователь, которому принадлежит тикет. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
                 foreignKey = @ForeignKey(name = "fk_ticket_user"))
    private UserEntity user;
}
