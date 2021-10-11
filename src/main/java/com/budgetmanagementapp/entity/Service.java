package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "service_title")
    private String title;

    @Column(name = "service_text")
    private String text;

    @Column(name = "service_icon")
    private String icon;
}
