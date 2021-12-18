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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_faq_with_translation_question",
            joinColumns = {@JoinColumn(name = "faq_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation question;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rel_faq_with_translation_answer",
            joinColumns = {@JoinColumn(name = "faq_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "translation_id", referencedColumnName = "id")})
    private Translation answer;
}
