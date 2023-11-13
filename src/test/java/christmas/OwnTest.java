package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.constant.Info;
import christmas.controller.PreviewController;
import christmas.domain.Badge;
import christmas.domain.Order;
import christmas.service.PreviewService;
import christmas.validator.Validator;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDate;
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
            LocalDate date = future.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 1초가 지난 후 byteArrayOutputStream.toString()이 Info.INVALID_DATE를 포함하는지 확인합니다.
            assertThat(byteArrayOutputStream.toString())
                    .contains(Info.ERROR_MSG_INVALID_DATE);
        }finally {
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
    void 올바른_주문의_총_가격을_계산합니다(String orderDetail, int totalPrice){
        Order order = Order.fromDetails(orderDetail);
        Assertions.assertEquals(totalPrice,order.calculateTotalPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"제로콜라-1", "양송이수프-21", "크리스마스파스타-1,크리스마스파스타-1"})
    void 주문이_올바르지_않습니다(String detail){
        Supplier<String> detailSupplier = () -> detail;
        Function<String, Order> orderMapper = previewService::parseInputToOrder;

        ExecutorService excutor = Executors.newSingleThreadExecutor();
        Future<Order> task = excutor.submit(
                () -> Validator.validate(detailSupplier, orderMapper, outputView::printErrorMsg)
        );

        try {
            task.get(500L, TimeUnit.MILLISECONDS);
        }catch (InterruptedException | TimeoutException | ExecutionException e){
            assertThat(byteArrayOutputStream.toString())
                    .contains(Info.ERROR_MSG_INVALID_ORDER);
        }finally {
            excutor.shutdown();
        }
    }
}
