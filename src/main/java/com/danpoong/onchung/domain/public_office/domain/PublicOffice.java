package com.danpoong.onchung.domain.public_office.domain;

import com.danpoong.onchung.domain.policy.domain.Policy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class PublicOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_office_id")
    private Long id;

    @Column(name = "name")
    private String name;

    // 필터링 시 사용
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;

    // 도로명 주소
    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;

    // 관공서 연락처
    @Column(name = "public_office_phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "publicOffices")  // 반대 방향 설정
    private List<Policy> policies = new ArrayList<>();

    @Builder
    public PublicOffice(String name) {
        this.name = name;
    }

    public void updateInfo(String roadAddress, String longitude, String latitude, String phoneNumber) {
        this.roadAddress = roadAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;

        splitAddress(roadAddress);
    }

    // 필터링을 위한
    private void splitAddress(String address) {
        String[] split = address.split(" ");

        this.state = split[0];

        if (split[1].endsWith("시")) {
            this.city = split[1];
        } else {
            this.city = "";
        }
    }
}