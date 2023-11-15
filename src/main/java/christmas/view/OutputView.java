package christmas.view;

import christmas.constant.Info;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    private final String PREVIEW_START_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리보기!\n\n";
    private final String ORDER_MENU = "<주문 메뉴>\n";
    private final String BEFORE_DISCOUNT = "<할인 전 총주문 금액>\n";
    private final String PRESENTATION_MENU = "<증정 메뉴>\n";
    private final String DISCOUNT_DETAILS = "<혜택 내역>\n";
    private final String TOTAL_DISCOUNT = "<총혜택 금액>\n";
    private final String EXPECT_PAYMENT = "<할인 후 예상 결제 금액>\n";
    private final String EVENT_BADGE = "<%d월 이벤트 배지>\n";
    private final String ERROR_PREFIX = "[ERROR] %s\n\n";

    public void printErrorMsg(String errorMsg) {
        System.out.printf(ERROR_PREFIX, errorMsg);
    }

    public void printPreviewStartMsg(int month, int day) {
        System.out.printf(PREVIEW_START_MESSAGE, month, day);
    }

    public void printUserOrderMenus(List<String> orderDetails) {
        String orderMenu = orderDetails.stream()
                .collect(Collectors.joining("\n", ORDER_MENU, "\n\n"));
        System.out.print(orderMenu);
    }

    public void printSummedOrderPrice(int totalPrice) {
        System.out.println(BEFORE_DISCOUNT + Info.WON_WITH_COMMA.format(totalPrice) + "\n");
    }

    public void printGiftInfo(String giftInfo) {
        System.out.println(PRESENTATION_MENU + giftInfo + "\n");
    }

    public void printAppliedEventDetails(String eventDetails) {
        System.out.println(DISCOUNT_DETAILS + eventDetails + "\n");
    }

    public void printTotalDiscountAmount(int totalDiscounts) {
        System.out.println(TOTAL_DISCOUNT + Info.WON_WITH_COMMA.format(totalDiscounts) + "\n");
    }

    public void printExpectedPayment(int afterDiscount) {
        System.out.println(EXPECT_PAYMENT + Info.WON_WITH_COMMA.format(afterDiscount) + "\n");
    }

    public void printEventBadge(int month, String badge) {
        System.out.printf((EVENT_BADGE + badge + "\n"), month);
    }
}
