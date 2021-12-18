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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_service_with_translation_title",
            joinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_service_with_translation_text",
            joinColumns = {@JoinColumn(name = "service_id   ", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation text;

    @Column(name = "service_icon")
    private String icon;
}
