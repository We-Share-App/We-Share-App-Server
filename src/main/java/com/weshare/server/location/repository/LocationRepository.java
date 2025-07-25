package com.weshare.server.location.repository;

import com.weshare.server.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {

    Optional<Location>findByStateNameAndCityNameAndTownName(String state, String city,String town);
    Optional<Location>findById(Long id);
}
