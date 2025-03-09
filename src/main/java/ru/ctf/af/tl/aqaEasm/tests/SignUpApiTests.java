package ru.ctf.af.tl.aqaEasm.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.ctf.af.tl.aqaEasm.helpers.CallUserForSignUpRequest;
import ru.ctf.af.tl.aqaEasm.helpers.ConfigReader;
import ru.ctf.af.tl.aqaEasm.helpers.TestsDataProviders;
import ru.ctf.af.tl.aqaEasm.helpers.User;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class SignUpApiTests {
    private static final String REQUEST_URL;

    static {
        try {
            REQUEST_URL = new ConfigReader().getApiUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod
    public static void setup() {
        RestAssured.baseURI = REQUEST_URL;
    }

    @Test(dataProvider = "apiInvalidFullNamesAndMessages", dataProviderClass = TestsDataProviders.class)
    @Description("Проверка валидации некорректных ФИО")
    @Feature("API валидация ФИО")
    @Step("Проверка валидации некорректных ФИО в API запросах")
    public void testInvalidUserFullNameErrors(String fullName, boolean type, String message) {
        CallUserForSignUpRequest callUserForSignUpRequestBody = new CallUserForSignUpRequest(new User());
        callUserForSignUpRequestBody.setUserName(fullName);
        Response response = callUserForSignUpRequestBody.callUserForSignUpResponse();
        response.then().statusCode(200).body("type", equalTo(type)).body("message", equalTo(message));
    }

    @Test(dataProvider = "apiInvalidPhoneAndMessages", dataProviderClass = TestsDataProviders.class)
    @Description("Проверка валидации некорректных номеров телефона")
    @Feature("API валидация номера телефона")
    @Step("Проверка валидации некорректных номеров телефона в API запросах")
    public void testInvalidUserPhoneErrors(String phone, boolean type, String message) {
        CallUserForSignUpRequest callUserForSignUpRequestBody = new CallUserForSignUpRequest(new User());
        callUserForSignUpRequestBody.setPhoneNumber(phone);
        Response response = callUserForSignUpRequestBody.callUserForSignUpResponse();
        response.then().statusCode(200).body("type", equalTo(type)).body("message", equalTo(message));
    }

    @Test
    @Description("Проверка валидации разных парелей на вводе и подтверждении")
    @Feature("API валидация паролей")
    @Step("Проверка валидации разных паролей в API запросах")
    /* Не валидируются разные пароли. Предполагается что упадет */ public void testInvalidUserPasswordsSignUp() {
        CallUserForSignUpRequest callUserForSignUpRequestBody = new CallUserForSignUpRequest(new User());
        callUserForSignUpRequestBody.setPasswordValidation("absolutely another password");
        Response response = callUserForSignUpRequestBody.callUserForSignUpResponse();
        response.then().statusCode(200).body("type", equalTo(false))
                /* Текст ошибки для примера (взят с UI). Но выводится ошибка недоступности эндпоинта, что предполагает, что запрос прошел проверки */
                .body("message", equalTo("Ваши пароли не совпадают"));
    }
}
