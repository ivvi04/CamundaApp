package ru.lakeevda.camundaapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Сущность пользователя (таблица camunda_app.users).
 */
@Entity
@Table(name = "users", schema = "camunda_app")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ФИО пользователя. */
    @Column(nullable = false)
    private String fio;

    /** Дата рождения. */
    @Column(nullable = false)
    private LocalDate birthday;

    /** Email пользователя. */
    @Column(nullable = false)
    private String email;
}
