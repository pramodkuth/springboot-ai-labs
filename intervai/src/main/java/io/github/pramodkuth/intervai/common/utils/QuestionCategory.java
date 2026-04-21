package io.github.pramodkuth.intervai.common.utils;

import java.util.ArrayList;
import java.util.List;

public class QuestionCategory {
    private static final List<String> CATEGORIES = new ArrayList<>();
    public static String nextCategory(){
        return CATEGORIES.removeFirst();
    }
    public static void reset(){
        CATEGORIES.clear();
        CATEGORIES.addAll(List.of("System Design", "SOLID principle", "DSA", "Coding"));
    }
    public static boolean isEmpty(){
        return CATEGORIES.isEmpty();
    }
}
