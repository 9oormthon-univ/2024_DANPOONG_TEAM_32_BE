package com.danpoong.onchung.global.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookieUtil {
    private static final int MAX_LIST_SIZE = 5;

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int expireSeconds) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(expireSeconds);
//        cookie.setSecure(true);  // 보안 쿠키로 설정 (HTTPS에서만 사용)
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie != null) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static void setLongListCookie(HttpServletResponse response, String cookieName, List<Long> longList, int expireSeconds) {
        if (longList.size() > MAX_LIST_SIZE) {
            longList.remove(0);
        }

        String cookieValue = longList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(expireSeconds);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static List<Long> getLongListFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    String cookieValue = cookie.getValue();

                    return Arrays.stream(cookieValue.split(","))
                            .map(Long::valueOf)
                            .collect(Collectors.toList());
                }
            }
        }
        return new ArrayList<>();
    }


    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
