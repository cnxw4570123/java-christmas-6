package christmas.domain;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface Event {
    public Optional<Event> caculateBenefit(Map<Menu, Integer> order, LocalDate date);

    public String showBenefitDetail();
}
