package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;

    private Long amount;

    @CreationTimestamp
    private LocalDateTime importAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("accountBook-import")
    @JoinColumn(name = "account_book_id", nullable = false)
    private AccountBook accountBook;
}
