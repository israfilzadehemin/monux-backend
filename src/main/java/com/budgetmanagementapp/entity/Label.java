package com.budgetmanagementapp.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
