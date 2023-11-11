package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.constant.Info;

public class InputView {
    private final String GREETING = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.\n";
    private final String INPUT_VISIT_DAY_MESSAGE = "%d 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n";

    private final String INPUT_ORDER_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려주세요. (e.g. 해산물파스타-2, 레드와인-1, 초코케이크-1)\n";

    public void printGreeting(){
        System.out.printf(GREETING, Info.THIS_MONTH);
    }

    public int inputVisitDate(){
        System.out.printf(INPUT_VISIT_DAY_MESSAGE, Info.THIS_MONTH);
        return Integer.parseUnsignedInt(Console.readLine());
    }

    public String inputOrders(){
        System.out.printf(INPUT_ORDER_MESSAGE);
        return Console.readLine();
    }

}
