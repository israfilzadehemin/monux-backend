package com.budgetmanagementapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            name = "rel_template_with_tag",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private List<Tag> tags;

    @ManyToOne()
    @JoinTable(
            name = "rel_template_with_user",
            joinColumns =
                    {@JoinColumn(name = "template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
