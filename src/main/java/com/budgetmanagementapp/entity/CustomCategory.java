package com.budgetmanagementapp.entity;

import com.budgetmanagementapp.utility.CategoryType;
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
public class CustomCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "custom_category_id")
    private String customCategoryId;

    @Column(name = "custom_category_icon")
    private String icon;

    @Column(name = "custom_category_name")
    private String name;

    @Column(name = "custom_category_type")
    private CategoryType type;

    @ManyToOne()
    @JoinTable(
            name = "rel_custom_category_with_user",
            joinColumns =
                    {@JoinColumn(name = "custom_category_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;

}
