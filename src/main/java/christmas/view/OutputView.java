package christmas.view;

import christmas.constant.Info;

public class OutputView {
    private final String PREVIEW_START_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리보기!\n";
    private final String ORDER_MENU = "<주문 메뉴>\n";
    private final String BEFORE_DISCOUNT = "<할인 전 총주문 금액>\n";
    private final String PRESENTATION_MENU = "<증정 메뉴>\n";
    private final String DISCOUNT_DETAILS = "<혜택 내역>\n";
    private final String TOTAL_DISCOUNT = "<총혜택 금액>\n";
    private final String EXPECT_PAYMENT = "<할인 후 예상 결제 금액>\n";
    private final String EVENT_BADGE = "<%d월 이벤트 배지>\n";
    private final String ERROR_PREFIX = "[ERROR] %s\n";

    public void printErrorMsg(String errorMsg) {
        System.out.printf(ERROR_PREFIX + Info.NEW_LINE, errorMsg);
    }

    public void printPreviewStartMsg(int month, int day) {
        System.out.printf(PREVIEW_START_MESSAGE + Info.NEW_LINE, month, day);
    }

    public void printUserOrderMenus(String orderDetails) {
        System.out.println(ORDER_MENU + orderDetails + Info.NEW_LINE);
    }

    public void printSummedOrderPrice(int totalPrice) {
        System.out.println(BEFORE_DISCOUNT + Info.WON_WITH_COMMA.format(totalPrice) + Info.NEW_LINE);
    }

    public void printGiftInfo(String giftInfo) {
        System.out.println(PRESENTATION_MENU + giftInfo + Info.NEW_LINE);
    }

    public void printAppliedEventDetails(String eventDetails) {
        System.out.println(DISCOUNT_DETAILS + eventDetails + Info.NEW_LINE);
    }

    public void printTotalDiscountAmount(int totalDiscounts) {
        System.out.println(TOTAL_DISCOUNT + Info.WON_WITH_COMMA.format(totalDiscounts) + Info.NEW_LINE);
    }

    public void printExpectedPayment(int afterDiscount) {
        System.out.println(EXPECT_PAYMENT + Info.WON_WITH_COMMA.format(afterDiscount) + Info.NEW_LINE);
    }

    public void printEventBadge(int month, String badge) {
        System.out.printf((EVENT_BADGE + badge + Info.NEW_LINE), month);
    }
}
