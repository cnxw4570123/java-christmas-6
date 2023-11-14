package christmas.domain;

import christmas.constant.Info;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Discount implements Event {
    private static final LocalDate D_DAY = LocalDate.of(Info.THIS_YEAR, Info.THIS_MONTH, 25);
    private static final LocalDate START_DAY = LocalDate.of(Info.THIS_YEAR, Info.THIS_MONTH, 1);
    private static final int START_DAY_DISCOUNT = 1_000;
    private static final int D_DAY_VARIABLE_DISCOUNT = 100;
    private static final int DAY_DISCOUNT = 2023;
    private static final int SPECIAL_DISCOUNT = 1_000;
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    private static final String WEEKDAY_DISCOUNT_MSG = "평일 할인";
    private static final String WEEKEND_DISCOUNT_MSG = "주말 할인";
    private static final String SPECIAL_DISCOUNT_MSG = "특별 할인";
    private static final String CHRISTMAS_D_DAY_DISCOUNT_MSG = "크리스마스 디데이 할인";
    private final String name;
    private final int discountAmount;


    private Discount(String name, int discountAmount) {
        this.name = name;
        this.discountAmount = discountAmount;
    }

    private static Discount getWeekDayDiscount(int count) {
        return new Discount(WEEKDAY_DISCOUNT_MSG, count * DAY_DISCOUNT);
    }

    private static Discount getWeekendDiscount(int count) {
        return new Discount(WEEKEND_DISCOUNT_MSG, count * DAY_DISCOUNT);
    }

    private static Discount getSpecialDiscount() {
        return new Discount(SPECIAL_DISCOUNT_MSG, SPECIAL_DISCOUNT);
    }

    private static Discount getChristmasDiscount(LocalDate date) {
        int discount = date.compareTo(START_DAY) * D_DAY_VARIABLE_DISCOUNT;
        return new Discount(CHRISTMAS_D_DAY_DISCOUNT_MSG, discount + START_DAY_DISCOUNT);
    }

    public Optional<Event> applyDayOfWeekDiscount(Map<MenuGroup, Integer> orderDetail, LocalDate visitDate) {
        if (WEEKEND.contains(visitDate.getDayOfWeek())) {
            return applyWeekendDiscount(orderDetail);
        }
        return applyWeekdayDiscount(orderDetail);
    }

    private static Optional<Event> applyWeekendDiscount(Map<MenuGroup, Integer> orderDetail) {
        if (orderDetail.containsKey(MenuGroup.MAIN_DISH)) {
            return Optional.of(getWeekendDiscount(orderDetail.get(MenuGroup.MAIN_DISH)));
        }
        return Optional.empty();
    }

    private static Optional<Event> applyWeekdayDiscount(Map<MenuGroup, Integer> orderDetail) {
        if (orderDetail.containsKey(MenuGroup.DESSERT)) {
            return Optional.of(getWeekDayDiscount(orderDetail.get(MenuGroup.DESSERT)));
        }
        return Optional.empty();
    }

    public Optional<Event> applySpecialDiscount(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(D_DAY)) {
            return Optional.of(getSpecialDiscount());
        }
        return Optional.empty();
    }

    public Optional<Event> applyChristmasDDayDiscount(LocalDate date){
        if (!date.isAfter(D_DAY)) {
            return Optional.of(getChristmasDiscount(date));
        }
        return Optional.empty();
    }

    @Override
    public String showBenefitDetail() {
        return name + Info.decimalFormat.format(discountAmount);
    }
}
