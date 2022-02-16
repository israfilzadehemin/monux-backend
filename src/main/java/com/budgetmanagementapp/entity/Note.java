package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;

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
