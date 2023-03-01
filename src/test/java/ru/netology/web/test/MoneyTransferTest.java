package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    DataHelper.AuthInfo user;
    DataHelper.VerificationCode code;
    DataHelper.Card cardOne;
    DataHelper.Card cardTwo;
    String cardOneId;
    String cardTwoId;
    String cardOneNumber;
    String cardTwoNumber;


    @BeforeEach
    public void init() {
        user = new DataHelper.AuthInfo("vasya", "qwerty123");
        code = new DataHelper.VerificationCode("12345");
        cardOne = new DataHelper.Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001", 10000);
        cardTwo = new DataHelper.Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002", 10000);
        cardOneId = cardOne.getId();
        cardTwoId = cardTwo.getId();
        cardOneNumber = cardOne.getNumber();
        cardTwoNumber = cardTwo.getNumber();

        open("http://localhost:9999/");

    }

    //Все расчёты ведутся в копейках
    @Test
    @DisplayName("Should transfer round amounts from 1 to 2")
    public void shouldTransferRoundAmountsFromOneToTwo() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceOneBefore > 100);

        int amount = (DataHelper.getRandomValue(1, balanceOneBefore / 100)) * 100;

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransferRound(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        int balanceTwoAfterExpected = balanceTwoBefore + amount;

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer round amounts from 2 to 1")
    public void shouldTransferRoundAmountsFromTwoToOne() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceTwoBefore > 100);

        int amount = (DataHelper.getRandomValue(1, balanceTwoBefore / 100)) * 100;

        dashboardPage
                .transferTo(cardOneId)
                .sendTransferRound(cardTwoNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        int balanceOneAfterExpected = balanceOneBefore + amount;
        int balanceTwoAfterExpected = balanceTwoBefore - amount;

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer from 1 to 2")
    public void shouldTransferFromOneToTwo() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceOneBefore > 0);

        int amount = DataHelper.getRandomValue(1, balanceOneBefore);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransfer(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        int balanceTwoAfterExpected = balanceTwoBefore + amount;

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer from 2 to 1")
    public void shouldTransferFromTwoToOne() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceTwoBefore > 1);

        int amount = DataHelper.getRandomValue(1, balanceTwoBefore);

        dashboardPage
                .transferTo(cardOneId)
                .sendTransfer(cardTwoNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        int balanceOneAfterExpected = balanceOneBefore + amount;
        int balanceTwoAfterExpected = balanceTwoBefore - amount;

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should not overlap")
    public void shouldNotOverlap() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceTwoBefore > 1);

        int amount = DataHelper.getRandomValue(balanceTwoBefore + 1, balanceTwoBefore + 10000);

        dashboardPage
                .transferTo(cardOneId)
                .sendTransfer(cardTwoNumber, amount);

        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceTwoAfter > 1);
    }

    @Test
    @DisplayName("Should not overlap round amounts")
    public void shouldNotOverlapRound() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);

        Assertions.assertTrue(balanceOneBefore > 1);

        int amount = DataHelper.getRandomValue(balanceOneBefore + 100, ((balanceOneBefore + 10000)/100)*100);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransferRound(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);

        Assertions.assertTrue(balanceOneAfter > 1);
    }

    @Test
    @DisplayName("Should transfer < 1 rub")
    public void shouldTransferLessThanOneRub() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);

        Assertions.assertTrue(balanceOneBefore > 0);

        int amount = DataHelper.getRandomValue(1, 100);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransfer(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        int balanceTwoAfterExpected = balanceTwoBefore + amount;

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }
}
