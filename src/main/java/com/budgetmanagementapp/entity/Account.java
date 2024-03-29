package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_name")
    private String name;

    @ManyToOne
    @JoinTable(
            name = "rel_account_with_account_type",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "account_type_id", referencedColumnName = "id")})
    private AccountType accountType;

    @ManyToOne
    @JoinTable(
            name = "rel_account_with_currency",
            joinColumns = {@JoinColumn(name = "acount_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "currency_id", referencedColumnName = "id")})
    private Currency currency;

    @Column(name = "allow_negative")
    private boolean allowNegative;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "show_in_sum")
    private boolean showInSum;

    @ManyToOne
    @JoinTable(
            name = "rel_account_with_user",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;

    @OneToMany(mappedBy = "senderAccount")
    private List<Transaction> transactionsOut;

    @OneToMany(mappedBy = "receiverAccount")
    private List<Transaction> transactionsIn;

    @OneToMany(mappedBy = "senderAccount")
    private List<Template> templatesOut;

    @OneToMany(mappedBy = "receiverAccount")
    private List<Template> templatesIn;

}
