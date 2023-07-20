package service;

import models.LoginResponseModel;
import io.restassured.response.Response;

import static constans.Urls.URL_LOGIN;
import static helpers.CustomsTextsSteps.postRequest;
import static specs.Specs.requestSpec;
import static specs.Specs.response200Spec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class RequestLoginUser {

    public static LoginResponseModel sendLogin(Object body) {
        return step(postRequest(URL_LOGIN.getUrl()), () ->
                given(requestSpec)
                        .when()
                        .body(body)
                        .post(URL_LOGIN.getUrl())
                        .then()
                        .spec(response200Spec)
                        .extract()
                        .response()
                        .as(LoginResponseModel.class));
    }

    public static Response sendLoginRaw(Object body) {
        return step(postRequest(URL_LOGIN.getUrl()), () ->
                given(requestSpec)
                        .when()
                        .body(body)
                        .post(URL_LOGIN.getUrl())
                        .then().extract().response());
    }

}
