package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_type")
    private String type;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JoinTable(
            name = "rel_transaction_with_account_from",
            joinColumns =
                    {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account senderAccount;

    @ManyToOne
    @JoinTable(
            name = "rel_transaction_with_account_to",
            joinColumns =
                    {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account receiverAccount;

    @ManyToOne
    @JoinTable(
            name = "rel_transaction_with_category",
            joinColumns =
                    {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Category category;

    @ManyToMany()
    @JoinTable(
            name = "rel_transaction_with_label",
            joinColumns =
                    {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    private List<Label> labels;

    @ManyToOne()
    @JoinTable(
            name = "rel_transaction_with_user",
            joinColumns =
                    {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
