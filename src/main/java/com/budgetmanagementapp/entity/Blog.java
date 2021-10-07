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

    @Column(name = "blog_title")
    private String title;

    @Column(name = "blog_text")
    private String text;

    @Column(name = "blog_icon")
    private String icon;
}
