//package com.project.api.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@EqualsAndHashCode(of = "id")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "account_book")
//public class AccountBook {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @OneToOne
//    @JsonBackReference
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private User user;
//
//    @Builder.Default
//    @JsonManagedReference("accountBook-expense")
//    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Expense> expenseList = new ArrayList<>();
//
//    @Builder.Default
//    @JsonManagedReference("import-expense")
//    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Import> importList = new ArrayList<>();
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//
//}
