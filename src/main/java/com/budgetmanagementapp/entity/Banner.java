package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "banner_id")
    private String bannerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_banner_with_translation_title",
            joinColumns = {@JoinColumn(name = "banner_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_banner_with_translation_text",
            joinColumns = {@JoinColumn(name = "banner_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation text;

    @Column(name = "banner_image")
    private String image;

    @Column(name = "banner_keyword")
    private String keyword;
}
