package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "translation")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "translation_id")
    private String translationId;

    @Column(name = "az")
    private String az;

    @Column(name = "en")
    private String en;

    @Column(name = "ru")
    private String ru;
}
