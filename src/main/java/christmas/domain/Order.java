package christmas.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.data.MapEntry;

public class Order {
    private final Map<Menu, Integer> detail;
    private static final int MAXIMUM_ORDER = 20;
    private static final String ORDER_PATTERN = "^([가-힣]{3,8}-[0-9]{1,2})(,[가-힣]{3,8}-[0-9]{1,2})*$";

    public static Order fromDetails(String input){
        Map<Menu, Integer> detail = validate(input);
        return new Order(detail);
    }

    private Order(Map<Menu, Integer> detail) {
        this.detail = detail;
    }

    public Map<MenuGroup, Integer> toCountByMenuGroup() {
        Map<MenuGroup, Integer> countByCategory = new HashMap<>();
        detail.forEach((menu, integer) -> {
            countByCategory.merge(MenuGroup.checkMenuGroup(menu), integer, Integer::sum);
        });
        return countByCategory;
    }

    public int calculateTotalPrice() {
        return detail.entrySet().stream()
                .map(entry -> entry.getKey().getMenuPrice() * entry.getValue())
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
    }

    private static Map<Menu, Integer> validate(String input) {
        if (!Pattern.matches(ORDER_PATTERN, input)) {
            throw new IllegalArgumentException();
        }
        Stream<MapEntry<Menu, Integer>> detailStream = validateMenuAndQuantity(input);
        Map<Menu, Integer> detailCandidate = validateDuplication(detailStream);
        validateExceedMaximumOrder(detailCandidate);
        validateDrinksOnly(detailCandidate);
        return detailCandidate;
    }

    private static Stream<MapEntry<Menu, Integer>> validateMenuAndQuantity(String input) {
        return Arrays.stream(input.split(","))
                .map(eachOrder -> {
                    String[] menuAndCount = eachOrder.split("-");
                    Menu menu = Menu.findMenuByName(menuAndCount[0]);
                    int count = Integer.parseUnsignedInt(menuAndCount[1]);
                    if (count <= 0) {
                        throw new IllegalArgumentException();
                    }
                    return MapEntry.entry(menu, count);
                });
    }

    private static Map<Menu, Integer> validateDuplication(Stream<MapEntry<Menu, Integer>> mapEntryStream) {
        Set<Menu> isDuplicated = new HashSet<>();

        return mapEntryStream.peek(eachOrder -> {
            if (!isDuplicated.add(eachOrder.getKey())) {
                throw new IllegalArgumentException();
            }
        }).collect(Collectors.toUnmodifiableMap(MapEntry::getKey, MapEntry::getValue));
    }

    private static void validateExceedMaximumOrder(Map<Menu, Integer> details) {
        int totalCount = details.values().stream()
                .reduce(Integer::sum)
                .orElseGet(() -> 0);
        if (totalCount > MAXIMUM_ORDER) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateDrinksOnly(Map<Menu, Integer> detailCandidate) {
        boolean isDrinksOnly = detailCandidate.keySet().stream()
                .map(MenuGroup::checkMenuGroup)
                .distinct()
                .noneMatch(menuGroup -> menuGroup == MenuGroup.APPETIZER || menuGroup == MenuGroup.MAIN_DISH
                        || menuGroup == MenuGroup.DESSERT);
        if (isDrinksOnly) {
            throw new IllegalArgumentException();
        }
    }

}
