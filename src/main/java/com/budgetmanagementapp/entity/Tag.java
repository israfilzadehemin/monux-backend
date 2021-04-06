package com.budgetmanagementapp.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "tag_id")
    private String tagId;

    @Column(name = "tag_name")
    private String name;

    @Column(name = "tag_visibility")
    private boolean visibility;

    @ManyToMany(mappedBy = "tags")
    private List<InOutTemplate> inOutTemplates;

    @ManyToMany(mappedBy = "tags")
    private List<InOutTransaction> inOutTransactions;
}
