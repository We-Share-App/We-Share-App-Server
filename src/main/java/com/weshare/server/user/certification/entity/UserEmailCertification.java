package com.weshare.server.user.certification.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_email_certification_number")
public class UserEmailCertification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "certification_key")
    private String certificationKey;

    @Column(name = "expiration_date_time")
    private LocalDateTime expirationDateTime;

    @Builder
    public UserEmailCertification(String emailAddress, String certificationKey, LocalDateTime expirationDateTime){
        this.emailAddress = emailAddress;
        this.certificationKey = certificationKey;
        this.expirationDateTime = expirationDateTime;
    }

    public String changeCertificationKey(String certificationKey){
        this.certificationKey = certificationKey;
        return certificationKey;
    }

    public LocalDateTime changeExpireDateTime(LocalDateTime expirationDateTime){
        this.expirationDateTime = expirationDateTime;
        return expirationDateTime;
    }
}
