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