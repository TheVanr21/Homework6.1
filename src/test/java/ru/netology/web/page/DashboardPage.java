package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement dashboardHeader = $("[data-test-id='dashboard']");
    private SelenideElement dashBoardMode = $("h1");
    private ElementsCollection cards = $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        isOpen();
    }

    public void isOpen() {
        dashboardHeader.shouldBe(Condition.visible);
        dashBoardMode.shouldHave(Condition.text("Ваши карты"));
    }

    private SelenideElement getCardById(String id) {
        return cards.find(Condition.attribute("data-test-id", id));
    }

    public int getCardBalance(String id) {
        String cardText = getCardById(id).getText();
        int start = cardText.indexOf(balanceStart);
        int finish = cardText.indexOf(balanceFinish);
        String value = cardText.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value + "00");
    }

    public TransferPage transferTo(String toId) {
        isOpen();
        getCardById(toId).$("button").click();
        return new TransferPage();
    }
}
