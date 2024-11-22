package com.project.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "import")
public class Import {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String category;

    private String description;

    private double amount;

    private LocalDateTime importAt;

    @ManyToOne
    @JoinColumn(name = "account_book_id", nullable = false)
    private AccountBook accountBook;
}
