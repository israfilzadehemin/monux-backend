package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Definition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "definition_id")
    private String definitionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_definition_with_translation_title",
            joinColumns = {@JoinColumn(name = "definition_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_definition_with_translation_text",
            joinColumns = {@JoinColumn(name = "definition_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation text;

    @Column(name = "definition_icon")
    private String icon;
}
