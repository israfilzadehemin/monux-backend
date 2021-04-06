package com.budgetmanagementapp.entity;

import java.time.LocalDateTime;
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
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "feedback_id")
    private String feedbackId;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinTable(
            name = "rel_feedback_with_user",
            joinColumns =
                    {@JoinColumn(name = "feedback_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
