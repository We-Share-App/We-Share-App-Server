package com.weshare.server.user.location.repository;

import com.weshare.server.location.entity.Location;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.location.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation,Long> {
    List<UserLocation> findAllByUser(User user);

    Optional<UserLocation> findByUserAndLocation(User user, Location location);

    Long countByUser(User user);
    Boolean existsByUserAndLocation(User user, Location location);
}
