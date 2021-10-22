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

    @Column(name = "step_title")
    private String title;

    @Column(name = "step_text")
    private String text;

    @Column(name = "step_icon")
    private String icon;

    @Column(name = "step_color")
    private String color;
}
