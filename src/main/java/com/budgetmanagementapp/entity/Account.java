package com.budgetmanagementapp.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_icon")
    private String icon;

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

    @OneToMany(mappedBy = "account")
    private List<InOutTransaction> inOutTransactions;

    @OneToMany(mappedBy = "accountFrom")
    private List<TransferTransaction> decTransferTransactions;

    @OneToMany(mappedBy = "accountTo")
    private List<TransferTransaction> incTransferTransactions;

    @OneToMany(mappedBy = "account")
    private List<DebtTransaction> debtTransactions;

    @OneToMany(mappedBy = "account")
    private List<InOutTemplate> inOutTemplates;

    @OneToMany(mappedBy = "accountFrom")
    private List<TransferTemplate> decTransferTemplates;

    @OneToMany(mappedBy = "accountTo")
    private List<TransferTemplate> incTransferTemplate;

    @OneToMany(mappedBy = "account")
    private List<DebtTemplate> debtTemplates;
}
