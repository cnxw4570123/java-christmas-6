package christmas.domain;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private final String name;
    private final int minValue;

    Badge(String name, int minValue) {
        this.name = name;
        this.minValue = minValue;
    }


    public static Badge checkBadge(int benefit){
        return Arrays.stream(Badge.values())
                .filter(badge -> benefit >= badge.minValue)
                .findFirst()
                .get();
    }
}
