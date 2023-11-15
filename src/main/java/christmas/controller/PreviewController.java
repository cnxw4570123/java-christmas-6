package christmas.controller;

import christmas.domain.Badge;
import christmas.domain.Event;
import christmas.domain.Order;
import christmas.service.PreviewService;
import christmas.validator.Validator;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PreviewController {
    private final PreviewService previewService;
    private final InputView inputView;
    private final OutputView outputView;

    public PreviewController(PreviewService previewService, InputView inputView, OutputView outputView) {
        this.previewService = previewService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void previewReceipt(){
        inputView.printGreeting();
        LocalDate visitDate = getValidatedVisitDate();
        outputView.printPreviewStartMsg(visitDate.getMonthValue(), visitDate.getDayOfMonth());
        Order userOrder = getValidatedOrder();
        printUserOrderPreview(userOrder, visitDate);
    }

    private void printUserOrderPreview(Order userOrder, LocalDate visitDate) {
        outputView.printUserOrderMenus(previewService.getOrderDetails(userOrder));
        int orderPrice = previewService.sumOrderPrice(userOrder);
        outputView.printSummedOrderPrice(orderPrice);
        List<Event> applicableEvents = previewService.getAllApplicableEvents(userOrder, visitDate);
        outputView.printGiftInfo(previewService.getGiftDetail(applicableEvents));
        String eventDetails = previewService.toEventDetails(applicableEvents);
        outputView.printAppliedEventDetails(eventDetails);
        int benefitAmount = previewService.sumBenefitAmount(applicableEvents);
        outputView.printTotalDiscountAmount(benefitAmount);
        outputView.printExpectedPayment(orderPrice - benefitAmount);
        Badge badge = previewService.calculateBadge(benefitAmount);
        outputView.printEventBadge(visitDate.getMonthValue(), badge.getName());
    }

    public LocalDate getValidatedVisitDate(){
        Supplier<Integer> inputDay = inputView::inputVisitDate;
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;
        return Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg);
    }

    public Order getValidatedOrder(){
        Supplier<String> inputOrder = inputView::inputOrders;
        Function<String, Order> parseInputToOrder = previewService::parseInputToOrder;
        return Validator.validate(inputOrder, parseInputToOrder, outputView::printErrorMsg);
    }
}
