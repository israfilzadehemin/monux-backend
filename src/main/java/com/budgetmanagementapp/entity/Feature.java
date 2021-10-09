package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "feature_id")
    private String featureId;

    @Column(name = "feature_content")
    private String content;

    @ManyToMany(mappedBy = "features", fetch = FetchType.EAGER)
    private List<Plan> plans;
}
