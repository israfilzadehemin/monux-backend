package com.budgetmanagementapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "note_id")
    private String noteId;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinTable(
            name = "rel_note_with_user",
            joinColumns =
                    {@JoinColumn(name = "note_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;

}
