package com.budgetmanagementapp.entity;

import java.time.LocalTime;
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
