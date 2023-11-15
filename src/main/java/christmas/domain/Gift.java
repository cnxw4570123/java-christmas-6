package christmas.domain;

import christmas.constant.Info;
import java.util.Optional;

public class Gift implements Event {
    private static final int MINIMUM_PURCHASE_FOR_GIFT = 120_000;
    private static final String GIFT_COUNT_MSG = "%d개";

    private static final String GIFT_EVENT_MSG = "증정 이벤트";

    private final Menu menu;
    private final int numberOfGift;
    private Gift(Menu gift, int count) {
        this.menu = gift;
        this.numberOfGift = count;
    }

    private static Gift getChampagne(){
        return new Gift(Menu.CHAMPAGNE, 1);
    }

    public static Optional<Event> applyGift(Integer totalPrice) {
        if (totalPrice >= MINIMUM_PURCHASE_FOR_GIFT) {
            return Optional.of(getChampagne());
        }
        return Optional.empty();
    }

    @Override
    public String showBenefitDetail(){
        return GIFT_EVENT_MSG + Info.COLON_WITH_SPACE + Info.MINUS + Info.WON_WITH_COMMA.format(menu.getMenuPrice());
    }

    public String showGiftDetail() {
        return menu.getMenuName()
                + " " + String.format(GIFT_COUNT_MSG, numberOfGift);
    }

    @Override
    public int getBenefitAmount(){
        return this.menu.getMenuPrice();
    }
}
