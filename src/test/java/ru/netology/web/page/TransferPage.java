package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        isOpen();
    }

    public void isOpen() {
        DashboardPage.isTransferMode();
    }


    public DashboardPage sendTransfer(String fromNumber, Integer amount) {
        amountField.setValue(amount / 100 + "," + amount % 100);
        fromField.setValue(fromNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage sendTransferRound(String fromNumber, Integer amount) {
        amountField.setValue("" + amount / 100);
        fromField.setValue(fromNumber);
        transferButton.click();
        return new DashboardPage();
    }
}
