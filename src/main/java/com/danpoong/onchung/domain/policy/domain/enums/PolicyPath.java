package com.danpoong.onchung.domain.policy.domain.enums;

public enum PolicyPath {
    예비창업자(1),
    공정한근로(2),
    커리어(3),
    첫보금자리(4),
    자취생활(5),
    미래를향한(6),
    문화(7),
    쉼표(8),
    함께(9),
    권리(10);

    private final int number;

    PolicyPath(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
