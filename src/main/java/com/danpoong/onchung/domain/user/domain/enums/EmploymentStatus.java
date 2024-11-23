package com.danpoong.onchung.domain.user.domain.enums;

import lombok.Getter;

@Getter
public enum EmploymentStatus {
    단기근로자,
    예비창업자,
    재직자_인턴,
    자영업자,
    사회초년생,
    직업훈련생,
    프리랜서,
    봉사활동가,
    제한없음;

//    SHORT_TERM_WORKER("단기근로자"),
//    ASPIRING_ENTREPRENEUR("예비창업자"),
//    EMPLOYEE_INTERN("재직자(인턴)"),
//    SELF_EMPLOYED("자영업자"),
//    YOUNG_PROFESSIONAL("사회초년생"),
//    VOCATIONAL_TRAINING("직업훈련생"),
//    FREELANCER("프리랜서"),
//    VOLUNTEER("봉사활동가");
//
//    private final String korean;
//
//    // 생성자
//    EmploymentStatus(String korean) {
//        this.korean = korean;
//    }
//
//    // 한글 값을 반환하는 메서드
//    public String getKorean() {
//        return korean;
//    }
//
//    // 한글을 기반으로 enum 값으로 변환하는 메서드
//    public static EmploymentStatus fromKorean(String korean) {
//        for (EmploymentStatus status : values()) {
//            if (status.getKorean().equals(korean)) {
//                return status;
//            }
//        }
//        throw new IllegalArgumentException("Unknown employment status: " + korean);
//    }
}

