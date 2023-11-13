package christmas;

import static org.assertj.core.api.Assertions.*;

import christmas.constant.Info;
import christmas.controller.PreviewController;
import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuGroup;
import christmas.domain.Order;
import christmas.service.PreviewService;
import christmas.validator.Validator;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class OwnTest {

    PreviewService previewService = new PreviewService();
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    PreviewController previewController = new PreviewController(previewService, inputView, outputView);

    private static ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @AfterEach
    void setSystemOut() {
        System.setOut(System.out);
    }


    @ParameterizedTest(name = "{index}회차 금액 = {0} 배지 = {1}")
    @CsvSource(value = {"50_000 : SANTA", "19_999 : TREE", "100 : NONE"}, delimiter = ':')
    void 금액에_알맞는_배지를_줍니다(int benefit, Badge badge) {
        Assertions.assertEquals(badge, Badge.checkBadge(benefit));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 13})
    void 날짜_검증_정상작동_확인(int test) {
        Supplier<Integer> inputDay = () -> test;
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;

        Assertions.assertTimeoutPreemptively(
                Duration.ofSeconds(1L),
                () -> {
                    LocalDate date = Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg);
                    Assertions.assertEquals(date.getDayOfMonth(), test);
                }
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {32, 0})
    void 날짜_검증_시_유효한_값을_넣어_주세요(int test) {
        Supplier<Integer> inputDay = () -> test;
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;

        // ExecutorService를 이용해 별도의 스레드에서 작업을 실행합니다.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<LocalDate> future = executor.submit(
                () -> Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg));

        try {
            // Future의 get 메소드를 이용해 결과를 가져옵니다.
            // 이 때, 1초가 지나도 결과가 반환되지 않으면 TimeoutException이 발생합니다.
            LocalDate date = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 1초가 지난 후 byteArrayOutputStream.toString()이 Info.INVALID_DATE를 포함하는지 확인합니다.
            assertThat(byteArrayOutputStream.toString())
                    .contains(Info.ERROR_MSG_INVALID_DATE);
        }

    }

    @Test
    void 올바른_카테고리별_메뉴_개수_계산() {
        // given
        Map<Menu, Integer> order = new HashMap<>();
        order.put(Menu.BARBECUE_RIB, 2);
        order.put(Menu.CHOCOLATE_CAKE, 1);
        order.put(Menu.RED_WINE, 1);
        order.put(Menu.T_BONE_STEAK, 10);
        Order order1 = new Order(order);

        // when
        Map<MenuGroup, Integer> countByMenuGroup = order1.toCountByMenuGroup();

        // then
        assertThat(countByMenuGroup)
                .contains(
                        Map.entry(MenuGroup.MAIN_DISH, 12),
                        Map.entry(MenuGroup.DESSERT, 1),
                        Map.entry(MenuGroup.DRINK, 1)
                );
    }

    @Test
    void 메뉴에_해당하는_메뉴그룹이_틀립니다(){
        // given
        Map<Menu, Integer> order = new HashMap<>();
        order.put(Menu.BARBECUE_RIB, 2);
        Order order1 = new Order(order);

        // when
        Map<MenuGroup, Integer> countByMenuGroup = order1.toCountByMenuGroup();

        // then
        assertThat(countByMenuGroup)
                .doesNotContain(
                        Map.entry(MenuGroup.APPETIZER, 2),
                        Map.entry(MenuGroup.DESSERT, 1),
                        Map.entry(MenuGroup.DRINK, 1)
                );
    }


}
