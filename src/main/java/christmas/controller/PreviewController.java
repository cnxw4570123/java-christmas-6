package christmas.controller;

import christmas.service.PreviewService;
import christmas.validator.Validator;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
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

    public void preview(){
    }

    public LocalDate getVisitDate(){
        Supplier<Integer> inputDay = inputView::inputVisitDate;
        Function<Integer, LocalDate> inputToVisitDate = previewService::parseInputToVisitDate;

        return Validator.validate(inputDay, inputToVisitDate, outputView::printErrorMsg);
    }

}
