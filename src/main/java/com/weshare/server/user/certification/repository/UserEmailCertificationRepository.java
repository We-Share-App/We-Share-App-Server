package com.weshare.server.user.certification.repository;

import com.weshare.server.user.certification.entity.UserEmailCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmailCertificationRepository extends JpaRepository<UserEmailCertification,Long> {

    Optional<UserEmailCertification> findByEmailAddress(String emailAddress);
    Boolean existsByEmailAddress(String emailAddress);
}
