package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "plan_title")
    private String title;

    @Column(name = "plan_text")
    private String text;

    @Column(name = "plan_price")
    private BigDecimal price;

    @Column(name = "plan_period_type")
    @Pattern(regexp = "MONTHLY|QUARTERLY|ANNUAL", message = "Invalid period type")
    private String periodType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rel_plan_feature",
            joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id")
    )
    private List<Feature> features;
}