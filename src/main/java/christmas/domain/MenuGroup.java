package christmas.domain;

import java.util.List;

public enum MenuGroup {
    APPETIZER("에피타이저", List.of(Menu.MUSHROOM_SOUP, Menu.TAPAS, Menu.CAESER_SALAD)),
    MAIN_DISH("메인", List.of(Menu.T_BONE_STEAK, Menu.BARBECUE_RIB, Menu.SEAFOOD_PASTA, Menu.CHRISTMAS_PASTA)),
    DESSERT("디저트", List.of(Menu.CHOCOLATE_CAKE, Menu.ICE_CREAM)),
    DRINK("음료", List.of(Menu.ZERO_COKE, Menu.RED_WINE, Menu.CHAMPAGNE));
    private String GroupName;
    private final List<Menu> menus;

    MenuGroup(String groupName, List<Menu> menus) {
        GroupName = groupName;
        this.menus = menus;
    }
}
