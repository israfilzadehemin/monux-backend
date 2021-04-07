package com.budgetmanagementapp.entity;

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
public class CustomTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "custom_tag_id")
    private String customTagId;

    @Column(name = "custom_tag_name")
    private String name;

    @Column(name = "custom_tag_visibility")
    private boolean visibility;

    @ManyToMany(mappedBy = "customTags")
    private List<InOutTemplate> inOutTemplates;

    @ManyToOne
    @JoinTable(
            name = "rel_custom_tag_with_user",
            joinColumns =
                    {@JoinColumn(name = "custom_tag_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
