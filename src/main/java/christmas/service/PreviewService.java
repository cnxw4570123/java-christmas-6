package christmas.service;

import christmas.constant.Info;
import java.time.LocalDate;

public class PreviewService {

    public LocalDate parseInputToVisitDate(int visitDay){
        return LocalDate.of(Info.THIS_YEAR, Info.THIS_MONTH, visitDay);
    }
}
