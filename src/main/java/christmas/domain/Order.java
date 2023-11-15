package christmas.domain;

import christmas.constant.Info;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.data.MapEntry;

public class Order {
    private final Map<Menu, Integer> detail;
    private static final int MAXIMUM_ORDER = 20;
    private static final String MENU_SEPARATOR = ",";
    private static final String MENU_COUNT_SEPARATOR = "-";
    private static final String ORDER_PATTERN = "^([가-힣]{3,8}-[0-9]{1,2})(,[가-힣]{3,8}-[0-9]{1,2})*$";

    private static final String ORDER_PRINTING_TEMPLATE = "%s %d개";
    private static final EnumSet<MenuGroup> nonDrinkGroups = EnumSet.of(MenuGroup.APPETIZER, MenuGroup.MAIN_DISH, MenuGroup.DESSERT);

    public static Order fromDetails(String input) {
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
                .mapToInt(entry -> entry.getKey().getMenuPrice() * entry.getValue())
                .sum();
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
        return Arrays.stream(input.split(MENU_SEPARATOR))
                .map(eachOrder -> {
                    String[] menuAndCount = eachOrder.split(MENU_COUNT_SEPARATOR);
                    Menu menu = Menu.findMenuByName(menuAndCount[0]);
                    int count = Integer.parseUnsignedInt(menuAndCount[1]);
                    if (count == 0) {
                        throw new IllegalArgumentException();
                    }
                    return MapEntry.entry(menu, count);
                });
    }

    private static Map<Menu, Integer> validateDuplication(Stream<MapEntry<Menu, Integer>> mapEntryStream) {
        return mapEntryStream.collect(
                Collectors.toUnmodifiableMap(
                        MapEntry::getKey,
                        MapEntry::getValue,
                        (k, v) -> {
                            throw new IllegalArgumentException();
                        }
                )
        );
    }

    private static void validateExceedMaximumOrder(Map<Menu, Integer> details) {
        int totalCount = details.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (totalCount > MAXIMUM_ORDER) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateDrinksOnly(Map<Menu, Integer> detailCandidate) {
        if (isDrinksOnly(detailCandidate)) {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isDrinksOnly(Map<Menu, Integer> detailCandidate) {
        return detailCandidate.keySet().stream()
                .map(MenuGroup::checkMenuGroup)
                .distinct()
                .noneMatch(nonDrinkGroups::contains);
    }

    public String detailToStrings() {
        return detail.entrySet().stream()
                .map(entry -> String.format(ORDER_PRINTING_TEMPLATE,
                        entry.getKey().getMenuName(), entry.getValue()))
                .collect(Collectors.joining(Info.NEW_LINE));
    }

}
