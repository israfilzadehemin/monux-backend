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
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "label_id")
    private String labelId;

    @Column(name = "label_name")
    private String name;

    @Column(name = "label_type")
    private String type;

    @Column(name = "label_visibility")
    private boolean visibility;

    @ManyToMany(mappedBy = "labels")
    private List<Template> templates;

    @ManyToMany(mappedBy = "labels")
    private List<Transaction> transactions;

    @ManyToOne
    @JoinTable(
            name = "rel_label_with_user",
            joinColumns =
                    {@JoinColumn(name = "label_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
