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

    @Column(name = "definition_title")
    private String title;

    @Column(name = "definition_text")
    private String text;

    @Column(name = "definition_icon")
    private String icon;
}
