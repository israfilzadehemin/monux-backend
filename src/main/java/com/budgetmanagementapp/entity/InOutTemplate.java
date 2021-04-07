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
public class InOutTemplate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "in_out_template_id")
    private String inOutTemplateId;


    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_type")
    private String transactionType;

    @ManyToOne
    @JoinTable(
            name = "rel_in_out_template_with_account",
            joinColumns =
                    {@JoinColumn(name = "in_out_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account account;

    @ManyToOne
    @JoinTable(
            name = "rel_in_out_template_with_category",
            joinColumns =
                    {@JoinColumn(name = "in_out_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Category category;

    @ManyToMany()
    @JoinTable(
            name = "rel_in_out_template_with_tag",
            joinColumns =
                    {@JoinColumn(name = "in_out_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private List<Tag> tags;

    @ManyToMany()
    @JoinTable(
            name = "rel_in_out_template_with_custom_tag",
            joinColumns =
                    {@JoinColumn(name = "in_out_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "custom_tag_id", referencedColumnName = "id")})
    private List<CustomTag> customTags;

    @ManyToOne()
    @JoinTable(
            name = "rel_in_out_template_with_user",
            joinColumns =
                    {@JoinColumn(name = "in_out_template_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
