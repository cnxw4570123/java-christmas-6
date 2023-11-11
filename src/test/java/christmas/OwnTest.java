package christmas;

import christmas.domain.Badge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OwnTest {

    @ParameterizedTest(name = "{index}회차 금액 = {0} 배지 = {1}")
    @CsvSource(value = {"50_000 : SANTA", "19_999 : TREE", "100 : NONE"}, delimiter = ':')
    void 금액에_알맞는_배지를_줍니다(int benefit, Badge badge){
        Assertions.assertEquals(badge, Badge.checkBadge(benefit));
    }
}
