package com.weshare.server.location;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_name", unique = true, length = 20)
    private String stateName;

    @Column(name = "city_name", unique = true, length = 20)
    private String cityName;

    @Column(name = "town_name", unique = true, length = 20)
    private String townName;
}
