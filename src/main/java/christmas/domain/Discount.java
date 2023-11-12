package christmas.domain;

import christmas.constant.Info;
import java.text.DecimalFormat;
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

    private DecimalFormat decimalFormat = new DecimalFormat(": -#,###원");


    private Discount(String name, int discountAmount) {
        this.name = name;
        this.discountAmount = discountAmount;
    }

    public Discount getWeekDayDiscount(int count) {
        return new Discount(WEEKDAY_DISCOUNT_MSG, count * DAY_DISCOUNT);
    }

    public Discount getWeekendDiscount(int count) {
        return new Discount(WEEKEND_DISCOUNT_MSG, count * DAY_DISCOUNT);
    }

    public Discount getSpecialDiscount() {
        return new Discount(SPECIAL_DISCOUNT_MSG, SPECIAL_DISCOUNT);
    }

    public Discount getChristmasDiscount(LocalDate date) {
        int discount = date.compareTo(START_DAY) * D_DAY_VARIABLE_DISCOUNT;
        return new Discount(CHRISTMAS_D_DAY_DISCOUNT_MSG, discount + START_DAY_DISCOUNT);

    }

    @Override
    public Optional<Event> caculateBenefit(Map<Menu, Integer> userOrders, LocalDate date) {
        return Optional.empty();
    }

    @Override
    public String showBenefitDetail() {
        return name + decimalFormat.format(discountAmount);
    }
}
