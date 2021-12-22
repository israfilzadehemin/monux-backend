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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_feature_with_translation",
            joinColumns = {@JoinColumn(name = "feature_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation content;

    @ManyToMany(mappedBy = "features", fetch = FetchType.EAGER)
    private List<Plan> plans;
}
