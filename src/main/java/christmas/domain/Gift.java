package christmas.domain;

import java.time.LocalDate;
import java.util.Map;

public class Gift implements Event {
    private static final int TERMS_OF_GIFT = 120_000;

    private final Menu MENU;

    private Gift(Menu gift) {
        this.MENU = gift;
    }

    public Gift getChampagne(){
        return new Gift(Menu.CHAMPAGNE);
    }

    @Override
    public Event caculateBenefit(Map<Menu, Integer> order, LocalDate date) {
        int totalPrice = order.values().stream()
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
        if (totalPrice >= TERMS_OF_GIFT) {
            return getChampagne();
        }
        return null;
    }
}
