package com.weshare.server.user.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; //  서비스내에서의 사용자 이름
    private String name; // 소셜 등록 사용자 이름
    private String email; // 사용자 이메일
    private String phoneNumber; // 사용자 전화번호
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
}
