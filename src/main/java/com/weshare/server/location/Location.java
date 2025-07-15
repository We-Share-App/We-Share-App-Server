package com.weshare.server.location;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location", uniqueConstraints = {@UniqueConstraint(columnNames = {"state_name", "city_name", "town_name"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_name", length = 20)
    private String stateName;

    @Column(name = "city_name", length = 20)
    private String cityName;

    @Column(name = "town_name", length = 20)
    private String townName;
}
