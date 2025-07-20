package com.weshare.server.user.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; //  서비스내에서의 사용자 식별이름 (고윳값)

    @Column(unique = true)
    private String nickname; // 서비스내 사용자 활동 이름

    @Column(nullable = false, length = 20)
    private String name; // 소셜 등록 사용자 이름

    @Column(nullable = false, unique = true)
    private String email; // 사용자 이메일

    @Column(nullable = false,name = "is_certificated")
    private Boolean isCertificated;

    private String phoneNumber; // 사용자 전화번호

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole; // 사용자 구분

    public User(String username,String name, String email, UserRole userRole){
        this.username = username;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public String changeName(String name){
        this.name = name;
        return name;
    }

    public String changeEmail(String email){
        this.email = email;
        return email;
    }

    public String changePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return phoneNumber;
    }

    public Boolean changeIsVerified(Boolean isCertificated){
        this.isCertificated = isCertificated;
        return isCertificated;
    }

    public String updateNickname(String nickname){
        this.nickname = nickname;
        return nickname;
    }
}
