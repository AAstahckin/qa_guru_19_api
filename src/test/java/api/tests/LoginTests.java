package api.tests;

import api.models.LoginBodyModel;
import api.models.LoginResponseModel;
import api.service.Requests;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static api.responseassertions.AssertionsResponseLoginApi.*;
import static api.service.Requests.sendPostRequest;
import static api.specs.Specs.response200Spec;
import static api.specs.Specs.response400Spec;
import static api.constans.ErrorsTexts.*;
import static api.constans.Urls.URL_LOGIN;

@Story("Авторизация")
@DisplayName("Авторизация API POST /login")
public class LoginTests extends TestBase{

    LoginBodyModel loginBody = new LoginBodyModel();

    @Test
    @Tag("sanity")
    @DisplayName("Проверка авторизации пользователя")
    @Description("Авторизация")
    public void positiveLoginTest() {
        loginBody.setEmail(login).setPassword(password);
        val response = sendPostRequest(URL_LOGIN.getUrl(), loginBody, LoginResponseModel.class, response200Spec);
        assertPositiveLoginApi(response);
    }

    @DisplayName("Негативный сценарий авторизации ")
    @Description("Проверка негативных сценариев с 400")
    @ParameterizedTest(name = "[user: {0}; pass:{1}]")
    @MethodSource("submitIncorrectParameters")
    public void negativeLoginTest(String user, String pass, String responseErrorText) {
        loginBody.setEmail(user).setPassword(pass);
        val response = sendPostRequest(URL_LOGIN.getUrl(), loginBody, LoginResponseModel.class, response400Spec);
        assertNegativeLoginApi(response, responseErrorText);
    }

    private static Stream<Arguments> submitIncorrectParameters() {
        Faker faker = new Faker();
        return Stream.of(
                Arguments.of(faker.internet().emailAddress(), faker.artist().name(), USER_NOT_FOUND.getValue()),
                Arguments.of(faker.internet().emailAddress(), "", MISSING_PASSWORD.getValue()),
                Arguments.of("", faker.artist().name(), MISSING_EMAIL_OR_USERNAME.getValue()));
    }

}
