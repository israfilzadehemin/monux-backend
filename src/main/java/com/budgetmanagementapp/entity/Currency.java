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
public class Currency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "currency_name")
    private String name;

    @OneToMany(mappedBy = "currency")
    private List<Account> accounts;
}
