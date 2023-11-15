package christmas;

import christmas.controller.PreviewController;
import christmas.service.PreviewService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        PreviewController previewController = new PreviewController(new PreviewService(), new InputView(),
                new OutputView());
        previewController.previewReceipt();
    }
}
