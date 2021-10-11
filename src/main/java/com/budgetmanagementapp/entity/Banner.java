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

    @Column(name = "banner_title")
    private String title;

    @Column(name = "banner_text")
    private String text;

    @Column(name = "banner_image")
    private String image;

    @Column(name = "banner_keyword")
    private String keyword;
}
