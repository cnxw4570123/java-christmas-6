package christmas.domain;

import java.time.LocalDate;
import java.util.Optional;

public class Gift implements Event {
    private static final int TERMS_OF_GIFT = 120_000;
    private static final String GIFT_MESSAGE = "%d개";

    private final Menu menu;
    private final int numberOfGift;
    private Gift(Menu gift, int count) {
        this.menu = gift;
        this.numberOfGift = count;
    }

    private static Gift getChampagne(){
        return new Gift(Menu.CHAMPAGNE, 1);
    }

    public static Optional<Event> applyGift(Integer totalPrice, LocalDate date) {
        if (totalPrice >= TERMS_OF_GIFT) {
            return Optional.of(getChampagne());
        }
        return Optional.empty();
    }

    @Override
    public String showBenefitDetail() {
        return menu.getMenuName()
                + " " + String.format(GIFT_MESSAGE, numberOfGift);
    }
}
