package ru.ctf.af.tl.aqaEasm.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import ru.ctf.af.tl.aqaEasm.helpers.ConfigReader;
import ru.ctf.af.tl.aqaEasm.helpers.TestsDataProviders;
import ru.ctf.af.tl.aqaEasm.helpers.User;
import ru.ctf.af.tl.aqaEasm.helpers.WebDriverFactory;
import ru.ctf.af.tl.aqaEasm.pages.SignUpPage;

import java.io.IOException;

public class SignUpUiTests {

    private WebDriver browser;
    private SignUpPage signUpPage;
    private final String otpSendAlertText = "Сейчас на ваш телефон поступит звонок или сообщение, последние 4 цифры являются кодом";

    @BeforeMethod
    public void setUpSession() throws IOException {
        browser = WebDriverFactory.createDriver();
        browser.manage().window().maximize();
        browser.get(new ConfigReader().getTestPageUrl());
        signUpPage = new SignUpPage(browser);
    }

    @AfterMethod
    public void quit() {
        browser.quit();
    }

    @Test
    @Description("Проверка регистрации пользователя при вводе неверного СМС-кодом")
    @Feature("Регистрация пользователя")
    @Step("Регистрация пользователя с некорректным СМС-кодом")
    public void checkSignUpWithRandomSmsCode() {
        User user = new User();
        signUpPage.isSubmitDisabled();
        signUpPage.fillUserDataAndSubmit(user);
        signUpPage.isSubmitEnabled();
        signUpPage.clickSubmit();
        signUpPage.checkAlertTextAndClose(otpSendAlertText);
        signUpPage.isSmsCodeConfirmDisabled();
        signUpPage.fillSmsCode(new Faker().number().digits(4));
        signUpPage.clickConfirmSmsCode();
        signUpPage.checkSmsCodeConfirmError();
    }

    @Test(dataProvider = "uiInvalidFullNamesError", dataProviderClass = TestsDataProviders.class)
    @Description("Проверка валидации ФИО при вводе некорректных данных")
    @Feature("Валидация данных в поле ФИО")
    @Step("Проверка ошибок валидации при вводе некорректного ФИО")
    public void checkInvalidFullNameErrors(String fullName, String expectedError) {
        User user = new User();
        user.setFullName(fullName);
        signUpPage.fillUserDataAndClickConfirm(user);
        signUpPage.checkFullNameFieldError(expectedError);
    }

    @Test
    @Description("Проверка валидации телефона после отправки СМС-кода")
    @Feature("Валидация данных в поле телефон")
    @Step("Проверка валидации номера телефона после инициации отправки СМС-кода")
    public void checkPhoneValidationAfterSmsCodeSend() {
        User user = new User();
        String phone = user.getPhone();
        user.setPhone("8");
        String wrongPhoneAlertText = "Неправильно указан номер телефона получателя";
        signUpPage.fillUserDataAndClickConfirm(user);
        signUpPage.checkAlertTextAndClose(wrongPhoneAlertText);
        signUpPage.enterPhone(phone);
        signUpPage.clickSubmit();
        signUpPage.checkAlertTextAndClose(otpSendAlertText);
        signUpPage.closeSmsCodeConfirmForm();
        signUpPage.enterPhone("8");
        signUpPage.clickSubmit();
        /* Далее по логике валидации телефона должна быть ошибка валидации номера, но телефон валидацию проходит и отправляется смс или звонок. предполагается, что тест упадет */
        signUpPage.checkAlertTextAndClose(wrongPhoneAlertText);
    }
}