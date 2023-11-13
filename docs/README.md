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

##### 메서드

- 메뉴 이름을 반환하는 메서드
  - signature : `public String getMenuName()`
  - return
    - String : 메뉴 이름
  - description
    - 현재 메뉴 이름을 반환하는 메서드

- 메뉴 가격을 반환하는 메서드
  - signature : `public int getMenuPrice()`
  - return
    - int : 메뉴 가격
  - description
    - 현재 메뉴의 가격을 반환하는 메서드

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

##### 메서드

- 메뉴 이름을 통해 메뉴 그룹을 찾는 메소드
- signature : `public MenuGroup checkMenuGroup(Menu menu)`
- return
  - MenuGroup : 해당 메뉴가 속하는 MenuGroup
- description
  - 메뉴 그룹을 돌면서 해당 메뉴 그룹에 전달받은 메뉴가 있는지 확인한다.
  - 해당 메뉴가 속한 그룹을 반환한다.


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

#### Event<T, R> `interface`

---

##### 메서드

- 혜택을 계산해주는 메서드
- signature : `public R caculateBenefit(T order, LocalDate date)`
- description
  - 날짜와 주문 목록을 받아 혜택을 계산해준다.

- 혜택 명목과 금액을 보여주는 메소드
- signature : `public String showBenefitDetail()`
- description
  - 현재 혜택의 이름과 금액을 문자열로 합쳐 반환한다.

---

#### Discount

---
Event<Map<MenuGroup, Integer>, List<Event>> 인터페이스의 구현체

---

##### 필드

- D_DAY 
  - 크리스마스 당일
- START_DAY
  - 이벤트 시작인 1일
- START_DAY_DISCOUNT
  - 1000원 부터 시작 
- D_DAY_VARIABLE_DISCOUNT
  - 100원부터 시작해서 26일 이전 방문날까지 100원씩 증가한다.
- DAY_DISCOUNT
  - 평일에는 디저트 2023원 할인, 주말에는 메인메뉴 2023원 할인
- SPECIAL_DISCOUNT
  - 별이 있는 날에는 1000원 할인 
- WEEKEND
  - 금요일과 토요일
- WEEKDAY_DISCOUNT_MSG
  - "평일 할인"
- WEEKEND_DISCOUNT_MSG
  - "주말 할인"
- SPECIAL_DISCOUNT_MSG
  - "특별 할인";
- CHRISTMAS_D_DAY_DISCOUNT_MSG
  - "크리스마스 디데이 할인"
- name
  - 할인 명목을 담을 필드
- discountAmount
  - 할인 금액을 담을 필드

##### 메서드

- 평일 할인 객체를 반환하는 메서드
- signature :`public Discount getWeekDayDiscount(int count)`
- return
  - 새 Discount 객체
- description
  - 이름이 평일 할인이고 디저트 메뉴 * 2023의 할인금액을 갖는 새 Discount 객체 반환

- 주말 할인 객체를 반환하는 메서드
- signature : `public Discount getWeekendDiscount(int count)` 
- return
  - 새 Discount 객체
- description
  - 이름이 주말 할인이고 메인 메뉴 * 2023의 할인금액을 갖는 새 Discount 객체 반환

- 스페셜 할인 객체를 반환하는 메서드
- signature : `public Discount getSpecialDiscount()`
- return
  - 새 Discount 객체
- description
  - 이름이 특별 할인이고 1000원의 할인금액을 갖는 새 Discount 객체 반환

- 디데이 할인 객체를 반환하는 메서드
- signature : `public Discount getChristmasDiscount(LocalDate date)`
- return
- 새 Discount 객체
- description
  - 이름이 크리스마스 디데이 할인이고 1000원 + 날짜 * 100의 할인금액을 갖는 새 Discount 객체 반환

- 혜택 명목과 금액을 보여주는 메소드
- signature : `public String showBenefitDetail()`
- description
  - 현재 어떤 할인 적용되었는지 이름과 할인액을 문자열로 합쳐 반환한다.

- 혜택을 계산해주는 메서드
- signature : `public List<Event> caculateBenefit(Map<MenuGroup, Integer> order, LocalDate date)`
- description
  - 카테고리 별 개수를 받아 어떤 할인이 적용되는지와 할인 금액을 계산해 새 Discount 객체를 생성한다.
  - 이후 25일과 같거나 이전일 경우 크리스마스 디데이 할인 Discount 객체를 추가한다.

---

#### Gift

---

Event<Integer, Optional<Event>>의 구현체

---

##### 필드

- TERMS_OF_GIFT
  -12만원 이상일 경우 증정품 증정 

- MENU
  - 증정품으로 줄 메뉴
- NUMBER_OF_GIFT
  - 증정품 개수

##### 메서드

- 샴페인을 생성하는 메서드
- signature : `public Gift getChampagne()`
- return
  - 샴페인 1개를 담은 Gift 객체 반환

- 혜택을 계산해주는 메서드
- signature : `public Optional<Event> caculateBenefit(Integer totalPrice, LocalDate date)`
- return
  - 샴페인 Gift 객체
  - optional.empty()
- description
  - 총 금액이 12만원 이상이면 샴페인을 증정한다.
  - 그렇지 못할 경우 `Optional.empty()`을 반환한다.

- 혜택 명목과 금액을 보여주는 메소드
- signature : `public String showBenefitDetail()`
- description
  - 현재 증정품의 이름과 증정품 개수를 문자열로 합쳐 반환한다.

---

#### Order

---
클라이언트가 입력한 문자열 주문을 데이터로 가짐

---

##### 필드

- 상세
  - 메뉴와 개수를 가지는 `Map`객체

##### 메서드

- 생성자 메소드
- signature : `public Order(String input)`
- error
  - `IllegalArgumentException`
    - 메뉴 형식이 안맞을 경우
    - 메뉴 개수의 총 합이 20을 초과할 경우
    - 메뉴가 없는 경우
- return
  - Order : 검증 완료 시 새로운 객체 생성
- description
  - 문자열을 받아 정규 표현식에 맞는지 확인하고 일치하지 않을 경우 `IllegalArgumentException`을 발생시킨다.
  - 일치할 경우 ","기준으로 문자열들을 나눈다.
  - 만약 개수가 0개면 `IllegalArgumentException`을 발생시킨다.
  - 나눈 문자열을 다시 "-"로 나누고 메뉴와 개수를 가지는 `MapEntry`객체로 바꾼다.
  - `Set`을 이용해 메뉴가 중복 될 경우 `IllegalArgumentException`을 발생시킨다.
  - 이를 `Collections.toMap()`으로 맵 객체로 바꾼다.
  - 바꾼 이후 Map객체의 value 더해 20이상이면 `IllegalArgumentException`을 발생시킨다.
  - Set에 있는 메뉴들 기반으로 DESSERT, APPETIZER, MAIN_DISH 중 하나라도 없는 경우 오류를 발생시킨다.

- 주문을 카테고리 별 개수로 바꿔주는 메소드
  - signature : `public Map<MenuGroup, Integer> toCountByMenuGroup()`
  - return
    - Map<MenuGroup, Integer>
  - description
    - order 객체를 `forEach`으로 순회하며 Menu로 MenuGroup을 찾는다.
    - Map에 해당 메뉴 그룹이 없으면 추가하고 해당 메뉴 그룹과 메뉴 개수를 초기 값으로 설정한다.
    - 있다면 해당 메뉴 그룹에 개수를 추가한다.
    - 모든 entry를 순회했다면 만들어진 Map<MenuGroup, Integer> 객체를 반환한다.
  
- 주문의 총 금액을 반환하는 메소드
  - signature : `public int calculateTotalPrice()`
  - return
    - int totalPrice
  - description
    - order를 `entrySet()`으로 순회한다.
    - 각 entry마다 메뉴의 가격과 entry의 저장된 개수를 곱한다.
    - 그렇게 나온 각 메뉴 가격들을 더한뒤 반환한다.

---

### service

---

#### PreviewService

---

##### 메서드

- 정수를 입력받아 날짜로 변환하는 메서드
  - signature : `public LocalDate parseInputToVisitDate(int visitDay)`
  - error
    - DateTimeException
      - 해당 달의 날짜 범위에 해당하지 않으면 발생 (ex. 12월 0일, 2월 29일)
  - return
    - LocalDate의 정적 메소드 `of()`를 활용해 2023년 12월 visitDay일을 반환한다.

- 문자열 주문을 입력 받아 `Order`객체로 변환하는 메서드



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

---
### controller

---
#### PreviewController

---
##### 필드

- PreviewService
- InputView
  - 입력을 받아오기 위한 UI
- OutputView
  - 출력을 하기 위한 UI

##### 메서드

- 입력 받은 방문일을 LocalDate로 만들어주는 메서드
  - signature : `public LocalDate getVisitDate()`
  - return
    - LocalDate : 입력받은 정수를 `previewService.parseInputToVisitDate()`로 가공한 날짜
  - description
    - Validator의 `Supplier`을 `Integer` 타입으로 지정 `inputView.inputVisitDate()`를 매핑한다.
    - Function 역시 Integer를 받아 LocalDate를 반환하는 parseInputToVisitDate메소드를 매핑한다.
    - `Validator.validate()`메서드를 이용해 날짜로 변환한다.

---
### validator

---
#### Validator

---

##### 메서드

- 각 요구사항에 맞는지 검증하는 메서드
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
    - function에 의해 정제된 R 값

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