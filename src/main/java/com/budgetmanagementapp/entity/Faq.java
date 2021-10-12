package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "faq_id")
    private String faqId;

    @Column(name = "faq_question")
    private String question;

    @Column(name = "faq_answer")
    private String answer;
}
