package com.budgetmanagementapp.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "paymentStatus")
    private String paymentStatus;

    @OneToOne(mappedBy = "user")
    private Otp otp;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private List<Template> templates;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @OneToMany(mappedBy = "user")
    private List<Label> labels;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rel_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

}
