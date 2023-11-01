package com.example.Library.util;

public class CheckUtils {
    public static void isStringValid(String input) {
        if (!input.isEmpty() && input.chars().allMatch(Character::isWhitespace)) {
        } else {
            throw new InvalidStringException("input is invalid");
        }
    }
}
