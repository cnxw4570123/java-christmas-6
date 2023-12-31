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

- 메뉴의 한글 이름으로 Menu를 반환하는 메서드
  - signature : `public Menu findMenuByName(String name)`
  - return
    - Menu : 일치하는 메뉴
  - description
    - 메뉴들을 순회해 메뉴 이름을 확인한다.
    - 일치하는 메뉴를 반환하고 없는 경우 `IllegalArgumentException`을 발생시킨다.
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
- signature : `public MenuGroup findMenuGroup(Menu menu)`
- return
  - MenuGroup : 해당 메뉴가 속하는 MenuGroup
- description
  - 메뉴 그룹을 돌면서 해당 메뉴 그룹에 전달받은 메뉴가 있는지 확인한다.
  - 해당 메뉴가 속한 그룹을 반환한다.

- 메뉴 이름을 반환하는 메서드
 - signature : `public String getMenuName`
 - description
   - 해당 메뉴의 이름을 반환한다.

- 메뉴 가격을 반환하는 메서드
  - signature : `public int getMenuPrice`
  - description
    - 해당 메뉴의 가격을 반환한다.


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
  - signature : `public static Badge fromBenefitAmount(int benefit)`
  - return
    - 혜택 금액에 해당하는 Badge
  - description
    - 혜택 금액을 확인하고 `Arrays.stream(Badge.values())`으로 각 뱃지마다 순회
    - 순회 중 `filter`로 해당 금액이상인지 체크한다.
    - 체크 후 조건에 해당하면 현재 뱃지 반환 없으면 다음 뱃지로 이동

- 뱃지 이름을 반환하는 메서드
  - signature : `public String getName()`
  - return
    - 뱃지 이름을 반환한다.

---

#### Event<T, R> `interface`

---

##### 메서드

- 혜택 명목과 금액을 보여주는 메소드
- signature : `public String showBenefitDetail()`
- description
  - 현재 혜택의 이름과 금액을 문자열로 합쳐 반환한다.

- 혜택 금액을 반환하는 메서드
  - signature : `public int getBenefitAmount()`
  - description
    - 현재 혜택받은 금액을 반환한다.


---

#### Discount

---
Event 인터페이스의 구현체

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
- signature :`private static Discount getWeekDayDiscount(int count)`
- return
  - 새 Discount 객체
- description
  - 이름이 평일 할인이고 디저트 메뉴 * 2023의 할인금액을 갖는 새 Discount 객체 반환

- 주말 할인 객체를 반환하는 메서드
- signature : `private static Discount getWeekendDiscount(int count)` 
- return
  - 새 Discount 객체
- description
  - 이름이 주말 할인이고 메인 메뉴 * 2023의 할인금액을 갖는 새 Discount 객체 반환

- 스페셜 할인 객체를 반환하는 메서드
- signature : `private static Discount getSpecialDiscount()`
- return
  - 새 Discount 객체
- description
  - 이름이 특별 할인이고 1000원의 할인금액을 갖는 새 Discount 객체 반환

- 디데이 할인 객체를 반환하는 메서드
- signature : `public static Discount getChristmasDiscount(LocalDate date)`
- return
- 새 Discount 객체
- description
  - 이름이 크리스마스 디데이 할인이고 1000원 + 날짜 * 100의 할인금액을 갖는 새 Discount 객체 반환

- 혜택 명목과 금액을 보여주는 메소드
- signature : `public String showBenefitDetail()`
- description
  - 현재 어떤 할인 적용되었는지 이름과 할인액을 문자열로 합쳐 반환한다.

- 요일 할인을 계산해주는 메서드
  - signature : `public static Optional<Event> applyDayOfWeekDiscount(Map<MenuGroup, Integer> orderDetail, LocalDate visitDate)`
  - return
    - Optional<Event> - 할인 적용 가능할 때
    - Optional.empty() - 할인 적용 불가능할 때
  - description
  - 방문 날짜가 주말인지 확인한다.
    - 주말일 경우 `applyWeekendDiscount`메소드를 통해 Optional<Event>객체를 반환한다.
    - 평일일 경우 `applyWeekdayDiscount`메소드를 통해 Optional<Event>객체를 반환한다.

- 주말 할인을 적용해주는 메서드
  - signature : `private static Optional<Event> applyWeekendDiscount(Map<MenuGroup, Integer> orderDetail)`
  - return
    - Optional<Event> or Optional.empty()
  - description
  - 주문 메뉴 그룹에 메인 메뉴가 있는지 확인한다.
  - 있으면 getWeekendDiscount를 통해 새 Discount 객체를 반환한다.
  - 없으면 Optional.empty를 반환한다.

- 평일 할인을 적용해주는 메서드
  - signature : `private static Optional<Event> applyWeekdayDiscount(Map<MenuGroup, Integer> orderDetail)`
  - return
    - Optional<Event> or Optional.empty()
  - description
    - 주문 메뉴 그룹에 디저트가 있는지 확인하고 없으면 Optional.empty()를 반환한다.
    - 있으면 getWeekdayDiscount를 통해 새 Discount 객체를 반환한다.

- 특별할인을 적용해주는 메서드
  - signature : `public static Optional<Event> applySpecialDiscount(LocalDate visitDate)`
  - return
    - Optional<Event> or Optional.empty()
  - description
    - 방문일이 일요일이거나 크리스마스 당일이면 새 Discount 객체를 반환한다.
    - 그렇지 않으면 Optional.empty()를 반환한다.

- 크리스마스 디데이 할인을 적용해주는 메서드
  - signature : `public static Optional<Event> applyChristmasDDayDiscount(LocalDate date)`
  - return
    - Optional<Event> or Optional.empty()
  - description
    - 방문일이 크리스마스 당일이거나 이전이면 새 Discount 객체를 반환한다.
    - 그렇지 않으면 Optional.empty()를 반환한다.

- 할인 금액을 반환하는 메서드
  - signature : `public int getBenefitAmount()`
  - return
    - 현재 할인 금액을 반환한다.

---

#### Gift

---

Event의 구현체

---

##### 필드

- TERMS_OF_GIFT
  -12만원 이상일 경우 증정품 증정 

- GIFT_COUNT
  - 개수

- GIFT_EVENT_MSG
  - 증정 이벤트

- MENU
  - 증정품으로 줄 메뉴
- NUMBER_OF_GIFT
  - 증정품 개수

##### 메서드

- 샴페인을 생성하는 메서드
- signature : `private static Gift getChampagne()`
- return
  - 샴페인 1개를 담은 Gift 객체 반환

- 혜택을 계산해주는 메서드
- signature : `public static Optional<Event> applyGift(Integer totalPrice, LocalDate date)`
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

- 증정품 개수를 반환하는 메서드
  - signature : `public String showGiftDetail()`
  - description
    - 메뉴 이름과 개수를 문자열로 합쳐 반환한다.

- 혜택 금액을 반환하는 메서드
  - signature : `public int getBenefitAmount()`
  - return
    - 증정품의 가격

---

#### Order

---
클라이언트가 입력한 문자열 주문을 데이터로 가짐

---

##### 필드

- detail
  - 메뉴와 개수를 가지는 `Map`객체
- MAXIMUM_ORDER
  - 한 번에 주문 가능한 최대 주문량 : 20
- ORDER_PATTERN
  - 메뉴 형식이 맞는지 검증하는 정규 표현식
- ORDER_PRINTING_TEMPLATE
  - 주문받은 메뉴를 매핑하기 위한 문자열

##### 메서드

- 생성자 메소드
- signature : `private Order(Map<Menu, Integer> detail)`
- return
  - 새 `Order` 객체
- description
  - 새 `Order`객체를 반환한다.

- 정적 팩토리 메서드
  - signature : `public static Order fromDetails(String detail)`
  - return
    - `validate()`메소드를 이용해 검증 후 검증이 완료되면 생성자를 통해 새 `Order`객체를 반환한다.

- 통합 검증 메서드
- signature : `private static Map<Menu, Integer> validate(String input)`
- return
  - Map<Menu, Integer> : 검증 완료 시 `Order`에 사용될 detail 생성
- description
  - 문자열을 받아 정규 표현식에 맞는지 확인하고 일치하지 않을 경우 `IllegalArgumentException`을 발생시킨다.
  - `validateMenuAndQuantity`메서드를 활용해 `MapEntry<Menu, Integer>`의 Stream으로 변환한다.
  - 이를 `validateDuplication`메서드로 `Map<Menu, Integer>` 객체로 변환한다.
  - `validateExceedMaximumOrder`과 `validateDrinksOnly`메소드를 활용해 검증한다.
  - 이상 없으면 만들어진 `Map<Menu, Integer>` 객체를 반환한다.

- 메뉴 이름과 수량을 확인하는 메서드
  - signature : `private static Stream<MapEntry<Menu, Integer>> validateMenuAndQuantity(String input)`
  - error
    - IllegalArgumentException
      - 해당 메뉴 이름이 없는 경우
      - 개수가 0개이하인 경우
  - return
    - Stream<MapEntry<Menu, Integer>>
      - detail에 넣을 `MapEntry` 객체들의 스트림
  - description
    - ","기준으로 문자열들을 나눈다.
    - 메뉴 이름과 개수를 가진 문자열을 "-"로 나눈다.
    - 메뉴 이름으로 Menu를 찾고 숫자를 정수로 바꾼다.
    - `MapEntry.entry()`로 key-value 형태로 묶는다.

- 메뉴가 중복인지 확인하는 메서드
  - signature : `private static Map<Menu, Integer> validateDuplication(Stream<MapEntry<Menu, Integer>> mapEntryStream)`
  - error
    - IllegalArgumentException
      - 메뉴가 중복인 경우
  - return
    - Map<Menu, Integer>
    - 중복이 없을 경우 Stream을 `Collectors.toMap`으로 수정 불가능한 Map으로 모은다.

- 메뉴 개수가 20개 이하인지 확인하는 메서드
  - signature : `private static void validateExceedMaximumOrder(Map<Menu, Integer> details)`
  - error
    - IllegalArgumentException
      - 총 메뉴 개수가 20개가 넘는 경우
  - description
    - details의 value들을 더해 20개를 초과하는지 검사한다.

- 음료수만 시켰는지 확인하는 메서드
  - signature : `private static void validateDrinksOnly(Map<Menu, Integer> detailCandidate)`
  - error
    - IllegalArgumentException
      - 음료수만 시킨 경우
  - description
    - 메뉴들을 순회하면서 메뉴 그룹을 찾는다.
    - 하나의 메뉴그룹들만 남긴 뒤 메뉴 그룹에 애피타이저, 메인메뉴, 디저트가 포함되는지 확인한다.
    - 포함되지 않을 경우 `IllegalArgumentException`을 발생시킨다.

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

- 갖고 있는 주문을 메뉴와 개수의 문자열 리스트로 바꿔주는 메소드
  - signature : `public List<String> detailToString()`
  - return
    - List<String> "메뉴이름 N개"와 같은 형식을 가진 문자열 리스트
  - description
    - Map의 각 원소마다 순회하면서 ORDER_PRINTING_TEMPLATE에 키인 메뉴이름과 값인 메뉴 개수를 매핑한다.
    - 이를 모아 List로 담는다.
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
  - signature : `public Order parseInputToOrder(String input)`
  - return
    - Order
      - 새 `Order` 객체를 반환한다.

- Order를 받아서 적용된 혜택들의 리스트를 반환받는 메소드
  - signature : `private void addApplicableDiscounts(Order userOrder, LocalDate visitDate, List<Event> applicableEvents)`
  - return
    - List<Event> : 적용가능한 이벤트들
  - description
    - applyAvailableDiscounts와 applyAvailableGift를 활용해 applicableEvent에 이벤트를 넣어 반환한다.

- 적용가능한 모든 할인을 적용해주는 메소드
  - signature : `private void applyAvailableDiscounts(Order userOrder, LocalDate visitDate, List<Event> applicableEvents)`
  - return
  - description
    - Discount 클래스의 apply---Discount 메소드들을 활용해 Optional<Event> 객체를 받아온다.
    - 해당 할인이 있으면 applicableEvents에 넣어준다.

- 적용가능한 모든 증정 이벤트를 적용해주는 메소드
  - signature : `private void applyAvailableGiftEvent(Order userOrder, List<Event> applicableEvents)`
  - description
    - Gift 클래스의 applyGift 메소드들을 활용해 Optional<Event> 객체를 받아온다.
    - 해당 증정품이 있으면 applicableEvents에 넣어준다.
    

- Order를 통해 총 주문 금액을 계산하는 메소드
  - signature : `public int sumOrderPrice(Order userOrder)`
  - return
    - int : 총 금액
  - description
    - Order 객체의 calculateTotalPrice를 통해 얻은 총 금액을 반환한다.

- Order를 카테고리 별 주문 개수로 바꾸는 메소드
  - signature : `public Map<MenuGroup, Integer> gatherMenuCountByMenuGroup(Order userOrder)`
  - return
    - Map<MenuGroup, Integer>
  - description
    - Order 객체의 toCountByMenuGroup으로 변환한 Map<MenuGroup, Integer> 를 반환한다.

- 총 혜택 금액을 계산해주는 메서드
  - signature : `public int sumBenefitAmount(List<Event> appliedEvents)`
  - return
    - 총 혜택 금액
  - description
    - 각 Event의 getBenefitAmount로 혜택 금액을 반환받는다.
    - 모두 더한다.

- 이벤트 배지를 계산해주는 메서드
  - signature : `public Badge calculateBadge(int totalBenefit)`
  - return
    - 해당하는 뱃지
  - description
    - Badge.checkBadge() 메서드로 해당하는 배지를 반환받는다.

- 주문 내역을 문자열 리스트로 반환하는 메서드
  - signature : `public getOrderDetails(Order userOrder)`
  - return
    - 주문 내역들
  - description
    - Order 객체의 detailToString() 메서드로 문자열 리스트를 반환받는다.

- 증정품을 문자열로 반환하는 메서드
  - signature : `public String getGiftDetail(List<Event> appliedEvents)`
  - description
    - Gift 객체가 존재하는지 확인하고 있으면 해당 객체의 getGiftDetail 메서드를 이용해 증정품 이름과 개수를 반환한다.
    - 없으면 "없음"을 반환한다.

- 적용된 모든 혜택을 문자열로 합쳐 반환하는 메서드
  - signature : `public String toEventDetail(List<Event> appliedEvents)`
  - description
    - 적용된 이벤트가 없으면 Info.EMPTY를 반환한다.
    - 이벤트 구현체들을 순회하면서 각 구현체의 `showBenefitDetail`메서드를 이용해 문자열로 바꾼다.
    - 이를 "\n"로 연결해 문자열을 반환한다.
    

---
### view

---

#### InputView

---
##### 상수

- GREETING_MENT
  - "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.\n"
- INPUT_VISIT_DAY_MESSAGE
  - "%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n"
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

- 미리보기 시작을 알리는 메서드
  - signature : `public void printPreviewStartMsg(int month, int day)`
  - description
    - 방문 날짜의 달과 일을 받아서 PREVIEW_START_MSG에 매핑한 후 출력한다.
    
- 주문 메뉴들을 출력하는 메서드
  - signature : `public void printUserOrderMenus(List<String> orderDetails)`
  - description
    - 주문 메뉴와 개수로 이루어진 문자열 리스트들을 받아서 ORDER_MENU와 함께 출력한다.
  
- 할인 전 총 주문 금액을 출력하는 메서드
  - signature : `public void printSummedOrderPrice(int totalPrice)`
  - description
    - 할인 전 총 주문 금액을 받아서 BEFORE_DISCOUNT와 함께 출력한다.
    
- 증정 메뉴를 출력하는 메서드
  - signature : `public void printGiftInfo(String giftInfo)`
  - description
    - 적용된 혜택 내역들을 DISCOUNT_DETAIL과 함께 출력한다.
    
- 혜택 내역을 출력하는 메서드
  - signature : `public void printAppliedEventDetails(String eventDetails)`
  - description
    -
- 총 혜택 금액을 출력하는 메서드
  - signature : `public void printTotalDiscountAmount(int totalDiscounts)`
  - description
    - 총 혜택 금액을 TOTAL_DISCOUNT와 함께 출력한다.
    
- 할인 후 예상 결제 금액을 출력하는 메서드
  - signature : `public void printExpectedPayment(int afterDiscount)`
  - description
    - 총 주문 금액에서 할인 금액을 뺀 금액을 받아서 EXPECT_PAYMENT와 함께 출력한다.
    
- 이벤트 배지를 출력하는 메서드
  - signature : `public void printEventBadge(int month, String badge)`
  - description
    - 현재 달과 이벤트 뱃지 이름을 받아서 EVENT_BADGE에 담아 출력한다.
  
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

- 미리보기 과정을 제어하는 메서드
  - signature : `public void previewReceipt()`
  - description
    - 환영 인사를 출력시킨다.
    - 방문일과 주문을 입력받고 검증에 성공하면 각각 날짜와 Order 객체로 반환한다.
    - 검증에 실패하면 올바른 주문을 입력할 때까지 에러메시지와 함께 다시 입력받는다.
    - Order와 LocalDate 객체를 활용해 printUserOrderPreview 메서드로 미리보기를 출력한다.

- 주문과 방문일을 서비스 레이어를 이용해 가공하고 이를 출력시키는 메소드
  - signature : `public void PrintUserOrderPreview(Order userOrder, LocalDate visitDate)`
  - description
    - 주문 메뉴와 개수를 OutputView로 전달해 출력한다.
    - 할인 전 주문 총 금액을 outputView로 전달해 출력한다.
    - 증정품 내역을을 outputView로 전달해 출력한다.
    - 적용된 모든 이벤트 내역을 outputView로 전달해 출력한다.
    - 적용된 이벤트의 총 혜택 금액을 outputView로 전달해 출력한다.
    - 총 금액에서 총 혜택 금액을 뺀 금액을 outputView로 전달해 출력한다.
    - 뱃지 이름을 outputView로 전달해 출력한다.
  

- 입력 받은 방문일을 LocalDate로 만들어주는 메서드
  - signature : `public LocalDate getValidatedVisitDate()`
  - return
    - LocalDate : 입력받은 정수를 `previewService.parseInputToVisitDate()`로 가공한 날짜
  - description
    - Validator의 `Supplier`을 `Integer` 타입으로 지정 `inputView.inputVisitDate()`를 매핑한다.
    - Function 역시 Integer를 받아 LocalDate를 반환하는 parseInputToVisitDate메소드를 매핑한다.
    - `Validator.validate()`메서드를 이용해 날짜로 변환한다.

- 입력 받은 주문을 Order로 만들어주는 메서드
  - signature : `public Order getValidatedOrder()`
  - return
    - Order : 입력받은 문자열을 `previewService.parseInputToOrder()`로 변환한 객체
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
- EMPTY
  - "없음"

- `DecimalFormat`
- WON_WITH_COMMA
  - 숫자를 천원단위로 ','와 함께 끊어줄 포매터