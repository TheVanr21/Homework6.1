package ru.netology.web.test;

import com.github.javafaker.Faker;
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
    String cardOneId;
    String cardTwoId;
    String cardOneNumber;
    String cardTwoNumber;
    Faker faker;

    @BeforeEach
    public void init() {
        faker = new Faker();
        user = DataHelper.getAuthInfo();
        code = DataHelper.getVerificationCodeFor(user);
        cardOneId = DataHelper.getCardId(1);
        cardTwoId = DataHelper.getCardId(2);
        cardOneNumber = DataHelper.getCardNumber(1);
        cardTwoNumber = DataHelper.getCardNumber(2);

        open("http://localhost:9999/");

    }

    @Test
    @DisplayName("Should successfully login")
    public void shouldSuccessfullyLogin() {

        new LoginPage()
                .validLogin(user)
                .validVerify(code);
    }

    //Все расчёты ведутся в копейках
    @Test
    @DisplayName("Should transfer round amounts from 1 to 2")
    public void shouldTransferRoundAmountsFromOneToTwo() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceOneBefore > 100);

        int amount = (faker.number().numberBetween(1, balanceOneBefore / 100)) * 100;
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransferRound(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        System.out.println("balanceOneAfterExpected="+balanceOneAfterExpected);
        int balanceTwoAfterExpected = balanceTwoBefore + amount;
        System.out.println("balanceTwoAfterExpected="+balanceTwoAfterExpected);

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer round amounts from 2 to 1")
    public void shouldTransferRoundAmountsFromTwoToOne() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceTwoBefore > 100);

        int amount = (faker.number().numberBetween(1, balanceTwoBefore / 100)) * 100;
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardOneId)
                .sendTransferRound(cardTwoNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        int balanceOneAfterExpected = balanceOneBefore + amount;
        System.out.println("balanceOneAfterExpected="+balanceOneAfterExpected);
        int balanceTwoAfterExpected = balanceTwoBefore - amount;
        System.out.println("balanceTwoAfterExpected="+balanceTwoAfterExpected);

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer from 1 to 2")
    public void shouldTransferFromOneToTwo() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceOneBefore > 0);

        int amount = faker.number().numberBetween(1, balanceOneBefore);
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransfer(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        System.out.println("balanceOneAfterExpected="+balanceOneAfterExpected);
        int balanceTwoAfterExpected = balanceTwoBefore + amount;
        System.out.println("balanceTwoAfterExpected="+balanceTwoAfterExpected);

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should transfer from 2 to 1")
    public void shouldTransferFromTwoToOne() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceTwoBefore > 1);

        int amount = faker.number().numberBetween(1, balanceTwoBefore);
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardOneId)
                .sendTransfer(cardTwoNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        int balanceOneAfterExpected = balanceOneBefore + amount;
        System.out.println("balanceOneAfterExpected="+balanceOneAfterExpected);
        int balanceTwoAfterExpected = balanceTwoBefore - amount;
        System.out.println("balanceTwoAfterExpected="+balanceTwoAfterExpected);

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }

    @Test
    @DisplayName("Should not overlap")
    public void shouldNotOverlap() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceTwoBefore > 1);

        int amount = faker.number().numberBetween(balanceTwoBefore + 1, balanceTwoBefore + 10000);
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardOneId)
                .sendTransfer(cardTwoNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        Assertions.assertTrue(balanceTwoAfter > 1);
    }

    @Test
    @DisplayName("Should not overlap round amounts")
    public void shouldNotOverlapRound() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceOneBefore > 1);

        int amount = faker.number().numberBetween(balanceOneBefore + 100, ((balanceOneBefore + 10000)/100)*100);
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransferRound(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        Assertions.assertTrue(balanceOneAfter > 1);
    }

    @Test
    @DisplayName("Should transfer < 1 rub")
    public void shouldTransferLessThanOneRub() {

        DashboardPage dashboardPage = new LoginPage().validLogin(user).validVerify(code);

        int balanceOneBefore = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneBefore="+balanceOneBefore);
        int balanceTwoBefore = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoBefore="+balanceTwoBefore);

        Assertions.assertTrue(balanceOneBefore > 0);

        int amount = faker.number().numberBetween(1, 100);
        System.out.println("amount="+amount);

        dashboardPage
                .transferTo(cardTwoId)
                .sendTransfer(cardOneNumber, amount);

        int balanceOneAfter = dashboardPage.getCardBalance(cardOneId);
        System.out.println("balanceOneAfter="+balanceOneAfter);
        int balanceTwoAfter = dashboardPage.getCardBalance(cardTwoId);
        System.out.println("balanceTwoAfter="+balanceTwoAfter);

        int balanceOneAfterExpected = balanceOneBefore - amount;
        System.out.println("balanceOneAfterExpected="+balanceOneAfterExpected);
        int balanceTwoAfterExpected = balanceTwoBefore + amount;
        System.out.println("balanceTwoAfterExpected="+balanceTwoAfterExpected);

        Assertions.assertEquals(balanceOneAfterExpected, balanceOneAfter);
        Assertions.assertEquals(balanceTwoAfterExpected, balanceTwoAfter);
    }
}
