package christmas.service;

import christmas.constant.Info;
import christmas.domain.Order;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class PreviewService {

    public LocalDate parseInputToVisitDate(int visitDay) {
        return LocalDate.of(Info.THIS_YEAR, Info.THIS_MONTH, visitDay);
    }

    public Order parseInputToOrder(String input) {
        return Order.fromDetails(input);
    }
}
