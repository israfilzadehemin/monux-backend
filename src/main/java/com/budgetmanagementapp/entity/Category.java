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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_icon")
    private String icon;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_type")
    private String type;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "category")
    private List<Template> templates;

    @ManyToOne()
    @JoinTable(
            name = "rel_category_with_user",
            joinColumns =
                    {@JoinColumn(name = "category_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;

}
