package christmas.domain;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        this.order = order;
    }

    public Map<MenuGroup, Integer> toCountByMenuGroup(){
        Map<MenuGroup, Integer> countByCategory = new HashMap<>();
        order.forEach((menu, integer) -> {
            countByCategory.merge(MenuGroup.checkMenuGroup(menu), integer, Integer::sum);
        });
        return countByCategory;
    }

    public int calculateTotalPrice(){
        return order.entrySet().stream()
                .map(entry -> entry.getKey().getMenuPrice() * entry.getValue())
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
    }

}
