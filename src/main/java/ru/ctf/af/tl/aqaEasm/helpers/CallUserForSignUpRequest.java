package ru.ctf.af.tl.aqaEasm.helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Getter
@Setter
public class CallUserForSignUpRequest {
    @Getter
    private static final String ENDPOINT;

    static {
        try {
            ENDPOINT = new ConfigReader().getCallUserForSignUpEndpoint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String login;
    private String password;
    private String passwordValidation;
    private String phoneNumber;
    private String userName;

    public CallUserForSignUpRequest(User user) {
        this.login = user.getEmail();
        this.password = user.getPassword();
        this.passwordValidation = user.getPassword();
        this.phoneNumber = user.getPhone();
        this.userName = user.getFullName();
    }

    public Response callUserForSignUpResponse() {
        return given()
                .contentType(ContentType.JSON)
                .body(this)
                .log().all()
                .when()
                .post(getENDPOINT())
                .then()
                .log().all()
                .extract().response();
    }
}
