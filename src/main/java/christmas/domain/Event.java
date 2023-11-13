package christmas.domain;

import java.time.LocalDate;
import java.util.Optional;

public interface Event<T, R> {
    public R caculateBenefit(T order, LocalDate date);

    public String showBenefitDetail();
}
