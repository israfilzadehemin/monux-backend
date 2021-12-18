package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "blog_id")
    private String blogId;

    @Column(name = "blog_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "blog_update_date")
    private LocalDateTime updateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_blog_with_translation_title",
            joinColumns = {@JoinColumn(name = "blog_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")} )
    private Translation title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_blog_with_translation_text",
            joinColumns = {@JoinColumn(name = "blog_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")} )
    private Translation text;

    @Column(name = "blog_image")
    private String image;
}
