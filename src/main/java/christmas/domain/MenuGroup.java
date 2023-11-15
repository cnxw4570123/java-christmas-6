package christmas.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MenuGroup {
    APPETIZER("에피타이저", List.of(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESER_SALAD)),
    MAIN_DISH("메인", List.of(Menu.T_BONE_STEAK, Menu.BARBECUE_RIB, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT("디저트", List.of(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM)),
    DRINK("음료", List.of(Menu.ZERO_COKE, Menu.RED_WINE, Menu.CHAMPAGNE)),

    ETC("없음", Collections.EMPTY_LIST);
    private final String GroupName;
    private final List<Menu> menus;

    MenuGroup(String groupName, List<Menu> menus) {
        GroupName = groupName;
        this.menus = menus;
    }

    public static MenuGroup checkMenuGroup(Menu menu){
        return Arrays.stream(MenuGroup.values())
                .filter(menuGroup -> menuGroup.menus.contains(menu))
                .findFirst()
                .orElse(ETC);
    }
}
