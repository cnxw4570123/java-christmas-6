package christmas.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Order {
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        this.order = order;
    }

    public int calculateTotalPrice(){
        return order.entrySet().stream()
                .map(entry -> entry.getKey().getMenuPrice() * entry.getValue())
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
    }

    public Map<MenuGroup, Integer> toCountByMenuGroup(){
        Map<MenuGroup, Integer> countByCategory = Arrays.stream(MenuGroup.values())
                .collect(Collectors.toMap(Function.identity(), menuGroup -> 0));
        order.forEach((menu, integer) -> {
            countByCategory.merge(MenuGroup.checkMenuGroup(menu), integer, Integer::sum);
        });
        return countByCategory;
    }

}
