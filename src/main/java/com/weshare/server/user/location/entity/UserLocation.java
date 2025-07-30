package com.weshare.server.user.location.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.location.entity.Location;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_location",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","location_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserLocation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Builder
    public UserLocation(User user, Location location){
        this.user = user;
        this.location = location;
    }
}
