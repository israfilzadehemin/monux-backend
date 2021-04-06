package com.budgetmanagementapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class DebtTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "debt_template_id")
    private String debtTemplateId;

    @ManyToOne
    @JoinTable(
            name = "rel_debt_template_with_transaction_type",
            joinColumns =
                    {@JoinColumn(name = "debt_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "transaction_type_id", referencedColumnName = "id")})
    private TransactionType transactionType;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinTable(
            name = "rel_debt_template_with_account",
            joinColumns =
                    {@JoinColumn(name = "debt_transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account account;

    @Column(name = "opposite_account")
    private String oppositeAccount;

    @ManyToOne
    @JoinTable(
            name = "rel_debt_template_with_user",
            joinColumns =
                    {@JoinColumn(name = "debt_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
