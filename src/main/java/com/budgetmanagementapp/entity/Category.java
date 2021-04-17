package com.budgetmanagementapp.entity;

import com.budgetmanagementapp.utility.CategoryType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<InOutTemplate> inOutTemplates;

    @OneToMany(mappedBy = "category")
    private List<InOutTransaction> inOutTransactions;
}
