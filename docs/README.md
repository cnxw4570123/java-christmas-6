### domain

#### enum Menu

##### 상수

- MUSHROOM_SOUP("양송이수프", 54_000),
- TAPAS("타파스", 5_500),
- CAESER_SALAD("시저 샐러드", 8000)
- T_BONE_STEAK("티본 스테이크", 55_000)
- BARBECUE_RIBS("바비큐립", 54_000)
- SEAFOOD_PASTA("해산물파스타", 35_000)
- CHRISTMAS_PASTA("크리스마스 파스타", 25_000)
- CHOCOLATE_CAKE("초코케이크", 15_000)
- ICE_CREAM("아이스크림", 5_000)
- ZERO_COKE("제로콜라", 3_000)
- RED_WINE("레드와인", 60_000)
- CHAMPAGNE("샴페인", 25_000)

##### 필드

- 메뉴 이름
- 메뉴 가격

---

#### enum MenuGroup
메뉴 종류에 따라 다른 메뉴를 가짐

---
##### 상수

- APPETIZER("에피타이저", List.of(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESER_SALAD))
- MAIN_DISH("메인", List.of(Menu.T_BONE_STEAK, Menu.BARBECUE_RIB, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA))
- DESSERT("디저트", List.of(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM))
- DRINK("음료", List.of(Menu.ZERO_COKE, Menu.RED_WINE, Menu.CHAMPAGNE))

##### 필드

- 메뉴 그룹 이름
- 각 그룹에 포함된 메뉴들

---

#### enum Badge

얼마 이상이면 배지를 가질 수 있는지를 나타내는 조건과 배지 이름을 가짐

---

##### 상수

- SANTA("산타", 20_000)
- TREE("트리", 10_000)
- STAR("별", 5_000)
- NONE("없음", 0)

##### 필드

- name
  - 뱃지 이름
- min_value
  - 뱃지 적용을 위해 받은 혜택의 최저 금액

##### 메서드

- 혜택 금액을 받아서 배지를 적용가능한지 확인하는 메서드
- signature : `public static Badge checkBadge(int benefit)`
- return
  - 혜택 금액에 해당하는 Badge
- description
  - 혜택 금액을 확인하고 `Arrays.stream(Badge.values())`으로 각 뱃지마다 순회
  - 순회 중 `filter`로 해당 금액이상인지 체크한다.
  - 체크 후 조건에 해당하면 현재 뱃지 반환 없으면 다음 뱃지로 이동

---

### view

---

#### InputView

---
##### 상수

- GREETING_MENT
  - "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.\n"
- INPUT_VISIT_DAY_MESSAGE
  - "%d 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n"
- INPUT_ORDER_MESSAGE

  - "주문하실 메뉴를 메뉴와 개수를 알려주세요. (e.g. 해산물파스타-2, 레드와인-1, 초코케이크-1)\n"
##### 메서드

- 환영인사를 출력해주는 메소드
  - signature : `public void printGreetingMent()`
  -  description
    - 내부 필드 thisMonth를 사용해 `System.out.printf`에 GREETING_MENT를 출력하는 메소드

- 방문 날짜를 입력받는 메소드
  - signature : `public String inputVisitDate()`
  - description
    - INPUT_VISIT_DAY_MESSAGE에 thisMonth를 넣어 출력한다.
    - `Console.readLine`메소드를 통해 입력받은 문자열을 반환한다.

- 메뉴와 개수를 입력받는 메소드
  - signature : `public String inputOrders()`
  - description
    - INPUT_ORDER_MESSAGE에 thisMonth를 넣어 출력한다.
    - `Console.readLine`메소드를 통해 입력받은 문자열을 반환한다.

---

#### OutputView

---
##### 필드

- PREVIEW_START_MESSAGE
  - "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리보기!"

- ORDER_MENU
  - "<주문 메뉴>"
- BEFORE_DISCOUNT
  - "<할인 전 총주문 금액>"
- GIFT_MENU
  - "<증정 메뉴>"
- DISCOUNT_DETAILS
  - "<혜택 내역>"
- TOTAL_DISCOUNT
  - "<총혜택 금액>"
- EXPECT_PAYMENT
  - "<할인 후 예상 결제 금액>"
- EVENT_BADGE
  - "<12월 이벤트 배지>"

- ERROR_PREFIX
  - "[ERROR] %s\n"

##### 메서드

- 주문 메뉴들을 출력하는 메서드

- 할인 전 총 주문 금액을 출력하는 메서드

- 증정 메뉴를 출력하는 메서드

- 혜택 내역을 출력하는 메서드

- 총 혜택 금액을 출력하는 메서드

- 할인 후 예상 결제 금액을 출력하는 메서드

- 이벤트 배지를 출력하는 메서드

- 에러를 출력하는 메서드
- signature : `public void printErrorMsg(String errorMsg)`
- description
  - 에러 메시지를 받아서 ERROR_PREFIX에 담아 출력하는 메소드


### validator

---
#### Validator

---

##### 메서드

각 요구사항에 맞는지 검증하는 메서드
- signature : `public static <T, R> R validate(Supplier<T> inputSupplier, Function<T, R> inputMapper, Consumer<String> errorHandler`
- error
  - `NumberFormatException` : 날짜, 메뉴 주문량이 숫자가 아닐 경우
  - `DateTimeException` : 날짜가 해당 달의 범위를 벗어날 경우
  - `IllegalArgumentException`
    - 메뉴판에 없는 메뉴를 입력하는 경우
    - 메뉴의 개수가 1개 미만일 경우
    - 메뉴 형식이 예시와 다른 경우
    - 메뉴가 중복인 경우
- return
  - function


---
### constant

---

#### Info

---
##### 상수

- `int`
- THIS_YEAR : 2023
- THIS_MONTH : 12

- `String`
- ERROR_MSG_INVALID_DATE
  - "유효하지 않은 날짜입니다. 다시 입력해주세요."
- ERROR_MSG_INVALID_ORDER 
  - "유효하지 않은 주문입니다. 다시 입력해주세요."