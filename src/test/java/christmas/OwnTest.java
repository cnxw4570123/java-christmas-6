package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import christmas.constant.Info;
import christmas.controller.PreviewController;
import christmas.domain.Badge;
import christmas.domain.Event;
import christmas.domain.Order;
import christmas.service.PreviewService;
import christmas.validator.Validator;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class OwnTest {
    private static ByteArrayOutputStream byteArrayOutputStream;

    private static Stream<Arguments> forDiscountTest() {
        return Stream.of(
                Arguments.of(4, "초코케이크-1,제로콜라-1", "평일 할인: -2,023원\n크리스마스 디데이 할인: -1,300원", "평일과 크리스마스 할인"),
                Arguments.of(8, "티본스테이크-1,제로콜라-1", "주말 할인: -2,023원\n크리스마스 디데이 할인: -1,700원", "주말과 크리스마스 할인"),
                Arguments.of(29, "티본스테이크-1,바비큐립-1,크리스마스파스타-1,제로콜라-1", "주말 할인: -6,069원\n증정 이벤트: -25,000원",
                        "주말 할인과 증정이벤트"),
                Arguments.of(31, "초코케이크-10", "평일 할인: -20,230원\n특별 할인: -1,000원\n증정 이벤트: -25,000원",
                        "평일과 특별 할인 그리고 증정이벤트"),
                Arguments.of(25, "초코케이크-10,제로콜라-1",
                        "평일 할인: -20,230원\n특별 할인: -1,000원\n크리스마스 디데이 할인: -3,400원\n증정 이벤트: -25,000원", "풀코스")
        );
    }

    private static Stream<Arguments> orderAndResultGenerator() {
        return Stream.of(
                Arguments.of("양송이수프-10,초코케이크-5,크리스마스파스타-5", List.of("양송이수프 10개", "초코케이크 5개", "크리스마스파스타 5개")),
                Arguments.of("아이스크림-1", List.of("아이스크림 1개")),
                Arguments.of("티본스테이크-1,양송이수프-1", List.of("티본스테이크 1개", "양송이수프 1개")),
                Arguments.of("제로콜라-1,레드와인-1,바비큐립-1", List.of("제로콜라 1개", "레드와인 1개", "바비큐립 1개"))
        );
    }

    PreviewService previewService = new PreviewService();
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    PreviewController previewController = new PreviewController(previewService, inputView, outputView);

    private final String LINE_SEPARATOR = System.lineSeparator();

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
        assertEquals(badge, Badge.fromBenefitAmount(benefit));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "13"})
    void 날짜_검증_정상작동_확인(String test) {
        Supplier<Integer> inputDay = () -> Integer.parseUnsignedInt(test);
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;

        assertTimeoutPreemptively(
                Duration.ofSeconds(1L),
                () -> {
                    LocalDate date = Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg);
                    assertEquals(Integer.toString(date.getDayOfMonth()), test);
                }
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"!", "-1", "32", "0"})
    void 날짜_검증_시_유효한_값을_넣어_주세요(String test) {
        // given
        Supplier<Integer> inputDay = () -> Integer.parseUnsignedInt(test);
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;

        // ExecutorService를 이용해 별도의 스레드에서 작업을 실행합니다.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<LocalDate> future = executor.submit(
                () -> Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg));

        try {
            // Future의 get 메소드를 이용해 결과를 가져옵니다.
            // 이 때, 1초가 지나도 결과가 반환되지 않으면 TimeoutException이 발생합니다.
            // when
            LocalDate date = future.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 1초가 지난 후 byteArrayOutputStream.toString()이 Info.INVALID_DATE를 포함하는지 확인합니다.
            // then
            assertThat(byteArrayOutputStream.toString())
                    .contains(Info.ERROR_MSG_INVALID_DATE);
        } finally {
            executor.shutdown();
        }

    }


    @ParameterizedTest
    @ValueSource(strings = {
            "제로콜라-1,레드와인-1,샴페인-1",
            "양송이수프-10,초코케이크-5,크리스마스파스타-6",
            "바베큐립-1,폭립-1,아이스크림-10",
            "아이스크림-0",
            "티본스테이크-1,양송이수프-1,양송이수프-1"})
    void 주문을_바르게_해주세요(String orderDetail) {
        assertThatThrownBy(() -> Order.fromDetails(orderDetail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "양송이수프-10,초코케이크-5,크리스마스파스타-5:260000",
            "아이스크림-1:5000",
            "티본스테이크-1,양송이수프-1:61000",
            "제로콜라-1,레드와인-1,바비큐립-1:117000"
    }, delimiter = ':')
    void 올바른_주문의_총_가격을_계산합니다(String orderDetail, int totalPrice) {
        // when
        Order order = Order.fromDetails(orderDetail);

        // then
        assertEquals(totalPrice, order.calculateTotalPrice());
    }

    @ParameterizedTest
    @MethodSource("orderAndResultGenerator")
    void 주문_확인_도와드리겠습니다(String orderDetail, List<String> args) {
        // when
        Order order = previewService.parseInputToOrder(orderDetail);
        String detailToStrings = previewService.getOrderDetails(order);
        String expect = String.join(LINE_SEPARATOR, args);
        // then
        assertThat(detailToStrings).contains(args);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "제로콜라-1",
            "양송이수프-15,제로콜라-3,티본스테이크-2,아이스크림-1",
            "크리스마스파스타-1,크리스마스파스타-1",
            "티본스페이크-1",
            "양송이 수프-1",
            "양송이수프-0"
    })
    void 주문이_올바르지_않습니다(String detail) {
        // given
        Supplier<String> detailSupplier = () -> detail;
        Function<String, Order> orderMapper = previewService::parseInputToOrder;

        ExecutorService excutor = Executors.newSingleThreadExecutor();
        Future<Order> task = excutor.submit(
                () -> Validator.validate(detailSupplier, orderMapper, outputView::printErrorMsg)
        );

        try {
            // when
            task.get(500L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            // then
            assertThat(byteArrayOutputStream.toString())
                    .contains(Info.ERROR_MSG_INVALID_ORDER);
        } finally {
            excutor.shutdown();
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "29:타파스-2,제로콜라-1,초코케이크-1", // 주말에 메인 없이
            "28:시저샐러드-1,티본스테이크-1,제로콜라-1", // 평일에 디저트 없이
            "26:아이스크림-1,제로콜라-1" // 평일에 디저트지만 이벤트 조건(10,000원 이상) 미충족
    }
            , delimiter = ':')
    void 적용된_할인이_없습니다(int visitDay, String order) {
        // given
        LocalDate visitDate = previewService.parseInputToVisitDate(visitDay);
        Order userOrder = previewService.parseInputToOrder(order);
        int orderPrice = previewService.sumOrderPrice(userOrder);
        int zero = 0;

        // when
        List<Event> applicableEvents = previewService.getAllApplicableEvents(orderPrice, userOrder, visitDate);

        // then
        assertEquals(applicableEvents.size(), zero);
    }


    @ParameterizedTest(name = "{0}일 {1} 주문 - {3}")
    @MethodSource({"forDiscountTest"})
    void 적용된_이벤트가_일치합니다(int visitDay, String order, String output, String explanation) {
        // given
        LocalDate localDate = previewService.parseInputToVisitDate(visitDay);
        Order userOrder = previewService.parseInputToOrder(order);
        int orderPrice = previewService.sumOrderPrice(userOrder);

        // when
        List<Event> applicableEvents = previewService.getAllApplicableEvents(orderPrice, userOrder, localDate);
        String applicableEventToString = previewService.toEventDetails(applicableEvents);

        // then
        assertEquals(output, applicableEventToString);

    }

    @ParameterizedTest
    @CsvSource(value = {
            "4:초코케이크-1,제로콜라-1:3323:NONE",
            "18:초코케이크-10,제로콜라-1:47930:SANTA", //22,930 + 증정품 25,000
            "1:크리스마스파스타-2:5046:STAR",
            "25:아이스크림-3:10469:TREE" //3400 + 6069
    }, delimiter = ':')
    void 뱃지를_잘_계산해주는지_확인합니다(int visitDay, String order, int totalBenefit, Badge badge) {
        // given
        Order inputToOrder = previewService.parseInputToOrder(order);
        LocalDate localDate = previewService.parseInputToVisitDate(visitDay);
        int orderPrice = previewService.sumOrderPrice(inputToOrder);

        // when
        List<Event> applicableEvents = previewService.getAllApplicableEvents(orderPrice, inputToOrder, localDate);
        int sumBenefitAmount = previewService.sumBenefitAmount(applicableEvents);

        // then
        assertEquals(totalBenefit, sumBenefitAmount);
        assertEquals(badge, previewService.calculateBadge(sumBenefitAmount));
    }
}
