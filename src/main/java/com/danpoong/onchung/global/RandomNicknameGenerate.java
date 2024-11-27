package com.danpoong.onchung.global;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomNicknameGenerate {
    private static final List<String> ADJECTIVES = List.of(
            "빠른", "느긋한", "예쁜", "멋진", "웃긴",
            "신나는", "달콤한", "부드러운", "깜찍한", "씩씩한",
            "용감한", "귀여운", "똑똑한", "재미있는", "우아한",
            "행복한", "상냥한", "기쁜", "화려한", "선명한"
    );

    private static final List<String> NOUNS = List.of(
            "호랑이", "토끼", "곰", "여우", "사자",
            "강아지", "고양이", "햇살", "달빛", "별",
            "꽃", "나무", "바람", "산", "구름",
            "물결", "파도", "숲", "새", "나비"
    );

    private final Random random = new Random();

    public String generateNickname() {
        String adjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(random.nextInt(NOUNS.size()));
        int randomNum = random.nextInt(100);

        return adjective + noun + randomNum;
    }
}
