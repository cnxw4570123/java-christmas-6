package christmas.domain;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class Gift implements Event {
    private static final int TERMS_OF_GIFT = 120_000;
    private static final String GIFT_MESSAGE = "%d개";

    private final Menu MENU;
    private final int NUMBER_OF_GIFT;
    private Gift(Menu gift, int count) {
        this.MENU = gift;
        this.NUMBER_OF_GIFT = count;
    }

    public static Gift getChampagne(){
        return new Gift(Menu.CHAMPAGNE, 1);
    }

    @Override
    public Optional<Event> caculateBenefit(Map<Menu, Integer> order, LocalDate date) {
        int totalPrice = order.values().stream()
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
        if (totalPrice >= TERMS_OF_GIFT) {
            return Optional.of(getChampagne());
        }
        return Optional.empty();
    }

    @Override
    public String showBenefitDetail() {
        return new StringBuilder(MENU.getMenuName())
                .append(" ").append(String.format(GIFT_MESSAGE, NUMBER_OF_GIFT))
                .append("\n").toString();
    }
}
