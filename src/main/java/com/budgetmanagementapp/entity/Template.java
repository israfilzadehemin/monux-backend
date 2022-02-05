package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_type")
    private String type;

    @ManyToOne
    @JoinTable(
            name = "rel_template_with_sender_account",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account senderAccount;

    @ManyToOne
    @JoinTable(
            name = "rel_template_with_receiver_account",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account receiverAccount;


    @ManyToOne
    @JoinTable(
            name = "rel_template_with_category",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Category category;

    @ManyToMany()
    @JoinTable(
            name = "rel_template_with_label",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "label_id", referencedColumnName = "id")})
    private List<Label> labels;

    @ManyToOne()
    @JoinTable(
            name = "rel_template_with_user",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
