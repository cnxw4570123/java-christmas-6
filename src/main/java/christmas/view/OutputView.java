package christmas.view;

public class OutputView {
    private final String PREVIEW_START_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리보기!";
    private final String ORDER_MENU = "<주문 메뉴>";
    private final String BEFORE_DISCOUNT = "<할인 전 총주문 금액>";
    private final String DISCOUNT_DETAILS = "<혜택 내역>";
    private final String TOTAL_DISCOUNT = "<총혜택 금액>";
    private final String EXPECT_PAYMENT = "<할인 후 예상 결제 금액>";
    private final String EVENT_BADGE = "<12월 이벤트 배지>";
    private final String ERROR_PREFIX = "[ERROR] %s\n";

    public void printErrorMsg(String errorMsg){
        System.out.printf(ERROR_PREFIX, errorMsg);
    }
}
