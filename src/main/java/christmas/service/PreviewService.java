package christmas.service;

import christmas.constant.Info;
import christmas.domain.Badge;
import christmas.domain.Discount;
import christmas.domain.Event;
import christmas.domain.Gift;
import christmas.domain.MenuGroup;
import christmas.domain.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreviewService {

    public LocalDate parseInputToVisitDate(int visitDay) {
        return LocalDate.of(Info.THIS_YEAR, Info.THIS_MONTH, visitDay);
    }

    public Order parseInputToOrder(String input) {
        return Order.fromDetails(input);
    }

    public List<Event> getAllApplicableEvents(int totalOrderPrice, Order userOrder, LocalDate visitDate){
        if (totalOrderPrice < Info.MINIMUM_PURCHASE_FOR_EVENT) {
            return Collections.emptyList();
        }

        List<Event> applicableEvents = new ArrayList<>();
        applyAvailableDiscounts(userOrder, visitDate, applicableEvents);
        applyAvailableGiftEvent(userOrder, applicableEvents);
        return applicableEvents;
    }

    private void applyAvailableDiscounts(Order userOrder, LocalDate visitDate, List<Event> applicableEvents) {
        Map<MenuGroup, Integer> groupByCategory = gatherMenuCountByMenuGroup(userOrder);
        Discount.applyDayOfWeekDiscount(groupByCategory, visitDate).ifPresent(applicableEvents::add);
        Discount.applySpecialDiscount(visitDate).ifPresent(applicableEvents::add);
        Discount.applyChristmasDDayDiscount(visitDate).ifPresent(applicableEvents::add);
    }

    private void applyAvailableGiftEvent(Order userOrder, List<Event> applicableEvents) {
        int totalPrice = sumOrderPrice(userOrder);
        Gift.applyGift(totalPrice).ifPresent(applicableEvents::add);
    }

    public int sumOrderPrice(Order userOrder){
        return userOrder.calculateTotalPrice();
    }

    public Map<MenuGroup, Integer> gatherMenuCountByMenuGroup(Order userOrder){
        return userOrder.toCountByMenuGroup();
    }

    public int sumBenefitAmount(List<Event> appliedEvents){
        return appliedEvents.stream()
                .mapToInt(Event::getBenefitAmount)
                .sum();
    }

    public Badge calculateBadge(int totalBenefit){
        return Badge.fromBenefitAmount(totalBenefit);
    }

    public String getOrderDetails(Order userOrder){
        return userOrder.detailToStrings();
    }

    public String getGiftDetail(List<Event> appliedEvents){
        return appliedEvents.stream().
                filter(event -> event instanceof Gift)
                .map(event -> ((Gift) event).showGiftDetail())
                .findFirst()
                .orElse(Info.EMPTY);
    }

    public String toEventDetails(List<Event> appliedEvents){
        if (appliedEvents.isEmpty()) {
            return Info.EMPTY;
        }

        return appliedEvents.stream()
                .map(Event::showBenefitDetail)
                .collect(Collectors.joining(Info.NEW_LINE));
    }
}
