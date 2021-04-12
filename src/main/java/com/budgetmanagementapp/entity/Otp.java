package com.budgetmanagementapp.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
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
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "otp_id")
    private String otpId;

    @Column(name = "otp")
    private String otp;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinTable(
            name = "rel_otp_with_user",
            joinColumns = {@JoinColumn(name = "otp_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")} )
    User user;
}
