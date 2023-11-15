package christmas.constant;

import java.text.DecimalFormat;

public class Info {
    public static final int THIS_YEAR = 2023;
    public static final int THIS_MONTH = 12;

    public static final int MINIMUM_PURCHASE_FOR_EVENT = 10_000;

    public static final String ERROR_MSG_INVALID_DATE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    public static final String ERROR_MSG_INVALID_ORDER = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public static final String EMPTY = "없음";
    public static final DecimalFormat WON_WITH_COMMA = new DecimalFormat("#,###원");
    public static final String NEW_LINE = System.lineSeparator();

    public static final String COLON_WITH_SPACE = ": ";
    public static final String MINUS = "-";
}
