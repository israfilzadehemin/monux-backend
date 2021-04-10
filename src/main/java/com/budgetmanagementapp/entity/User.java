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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "paymentStatus")
    private boolean paymentStatus;

    @OneToMany(mappedBy = "user")
    private List<Otp> otps;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private List<InOutTransaction> inOutTransactions;

    @OneToMany(mappedBy = "user")
    private List<InOutTemplate> inOutTemplates;

    @OneToMany(mappedBy = "user")
    private List<TransferTransaction> transferTransactions;

    @OneToMany(mappedBy = "user")
    private List<TransferTemplate> transferTemplates;

    @OneToMany(mappedBy = "user")
    private List<DebtTransaction> debtTransactions;

    @OneToMany(mappedBy = "user")
    private List<DebtTemplate> debtTemplates;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    @OneToMany(mappedBy = "user")
    private List<CustomCategory> customCategories;

    @OneToMany(mappedBy = "user")
    private List<CustomTag> customTags;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")
    private List<CustomNotification> customNotifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rel_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

}
