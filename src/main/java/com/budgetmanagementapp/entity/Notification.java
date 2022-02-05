package com.budgetmanagementapp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "notification_name")
    private String name;

    @Column(name = "notification_frequency")
    private String frequency;

    @Column(name = "notification_time")
    private LocalTime time;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    @JoinTable(
            name = "rel_notification_with_user",
            joinColumns =
                    {@JoinColumn(name = "notification_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;
}
