package christmas.domain;

import java.time.LocalDate;
import java.util.Map;

public interface Event {
    public Event caculateBenefit(Map<Menu, Integer> order, LocalDate date);
}
