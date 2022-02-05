package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_type_id")
    private String accountTypeId;

    @Column(name = "account_type_name")
    private String accountTypeName;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
