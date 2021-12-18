package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "step_id")
    private String stepId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_step_with_translation_title",
            joinColumns = {@JoinColumn(name = "step_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_step_with_translation_text",
            joinColumns = {@JoinColumn(name = "step_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation text;

    @Column(name = "step_icon")
    private String icon;

    @Column(name = "step_color")
    private String color;
}
