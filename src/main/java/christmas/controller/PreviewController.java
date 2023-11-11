package christmas.controller;

import christmas.service.PreviewService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PreviewController {
    private final PreviewService previewService;
    private final InputView inputView;
    private final OutputView outputView;

    public PreviewController(PreviewService previewService, InputView inputView, OutputView outputView) {
        this.previewService = previewService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

}
