package christmas.domain;

import christmas.constant.Info;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Discount implements Event<Map<MenuGroup, Integer>, List<Event>> {
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

    private final DecimalFormat decimalFormat = new DecimalFormat(": -#,###원");


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

    /*
    평일 할인과 주말할인의 경우 중복될 수 없기 때문에 무조건 적용이 된다.
    그 둘은 명확하게 메뉴 종류와 요일로 나뉘기 때문에 상관이 없음.
    그러나 특별할인과 크리스마스 디데이 할인은 애매함.
    MenuGroup이 애피타이저, 메인메뉴, 디저트, 음료까지 4개 다 들어오지만
    만약 해달 날이 일요일이거나 D-Day면 크리스마스 디데이 할인은 적용이 안됨.
     */
    @Override
    public List<Event> caculateBenefit(Map<MenuGroup, Integer> userOrder, LocalDate date) {
        List<Event> benefits = new ArrayList<>();
        userOrder.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(order ->{
                    if(order.getKey() == MenuGroup.DESSERT && !WEEKEND.contains(date.getDayOfWeek())){
                        benefits.add(getWeekDayDiscount(order.getValue()));
                        return;
                    }
                    if(order.getKey() == MenuGroup.MAIN_DISH && WEEKEND.contains(date.getDayOfWeek())){
                        benefits.add(getWeekendDiscount(order.getValue()));
                    }
                });
        if(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(D_DAY)){
            benefits.add(getSpecialDiscount());
        }
        if (!date.isAfter(D_DAY)) {
            benefits.add(getChristmasDiscount(date));
        }
        return benefits;
    }

    @Override
    public String showBenefitDetail() {
        return name + decimalFormat.format(discountAmount);
    }
}
