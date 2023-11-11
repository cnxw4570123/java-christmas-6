package christmas.validator;

import christmas.constant.Info;
import java.time.DateTimeException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Validator {
    public static <T, R> R validate(
            Supplier<T> inputSupplier,
            Function<T, R> inputMapper,
            Consumer<String> errorHandler) {
        while (true) {
            try {
                T input = inputSupplier.get();
                return inputMapper.apply(input);
            } catch (NumberFormatException | DateTimeException e) {
                // order일 경우도 메뉴 개수가 숫자임
                errorHandler.accept(Info.ERROR_MSG_INVALID_DATE);
            } catch (IllegalArgumentException e) {
                errorHandler.accept(Info.ERROR_MSG_INVALID_ORDER);
            }
        }
    }
}
