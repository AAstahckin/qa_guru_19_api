package api.service;

import api.models.UpdateUserResponseModel;

import static api.constans.Urls.URL_USER;
import static api.helpers.CustomsTextsSteps.putRequest;
import static api.specs.Specs.requestSpec;
import static api.specs.Specs.response200Spec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class RequestUpdateUser {

    public static String getUserUrl(int id) {
        return String.format(URL_USER.getUrl(), id);
    }

    public static UpdateUserResponseModel putUpdateUser(Object body, int userId) {
        return step(putRequest(getUserUrl(userId)), () ->
                given(requestSpec)
                        .when()
                        .body(body)
                        .put(getUserUrl(userId))
                        .then()
                        .spec(response200Spec)
                        .extract()
                        .response()
                        .as(UpdateUserResponseModel.class));
    }

    public static UpdateUserResponseModel patchUpdateUser(Object body, int userId) {
        return step(putRequest(getUserUrl(userId)), () ->
                given(requestSpec)
                        .when()
                        .body(body)
                        .patch(getUserUrl(userId))
                        .then()
                        .spec(response200Spec)
                        .extract()
                        .response()
                        .as(UpdateUserResponseModel.class));
    }

}
