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
public class TransferTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "transafer_transaction_id")
    private String transferTransactionId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinTable(
            name = "rel_transfer_transaction_with_from",
            joinColumns =
                    {@JoinColumn(name = "transfer_transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account accountFrom;

    @ManyToOne
    @JoinTable(
            name = "rel_transfer_transaction_with_to",
            joinColumns =
                    {@JoinColumn(name = "transfer_transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account accountTo;


    @ManyToOne
    @JoinTable(
            name = "rel_transfer_transaction_with_user",
            joinColumns =
                    {@JoinColumn(name = "transfer_transaction_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
