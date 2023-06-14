package api;

import api.models.RegisterBodyModel;
import api.models.RegisterResponseModel;
import api.service.Requests;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static api.specs.Specs.response200Spec;
import static api.specs.Specs.response400Spec;
import static data.ErrorsTexts.*;
import static data.TestLoginDataParams.LOGIN;
import static data.TestLoginDataParams.TOKEN;
import static data.Urls.URL_REGISTER;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Регистрация API /register")
public class RegisterTests {
    RegisterBodyModel bodyModel = new RegisterBodyModel();
    Faker faker = new Faker();

    @Test
    @DisplayName("Проверка регистрации пользователя API /register")
    @Description("Регистрация")
    public void positiveRegisterTest() {
        bodyModel.setEmail(LOGIN.getValue()).setPassword(faker.artist().name());
        RegisterResponseModel response = Requests.sendPostRequest(
                URL_REGISTER.getUrl(), bodyModel, RegisterResponseModel.class, response200Spec);
        step("Проверяем что присутствует id", () -> assertEquals(response.getId(), 4));
        step("Проверяем что присутствует token : " + TOKEN.getValue(), () -> assertEquals(response.getToken(), TOKEN.getValue()));
    }

    @DisplayName("Проверка негативных сценариев с 400 кодом API /register")
    @Description("Проверка негативных сценариев с 400")
    @ParameterizedTest(name = "[user: {0}; pass:{1}]")
    @MethodSource("submitIncorrectParameters")
    public void negativeTest(String user, String pass, String responseErrorText) {
        bodyModel.setEmail(user).setPassword(pass);
        RegisterResponseModel response = Requests.sendPostRequest(
                URL_REGISTER.getUrl(), bodyModel, RegisterResponseModel.class, response400Spec);
        step("Проверяем что присутствует ошибка : " + responseErrorText, () -> assertEquals(response.getError(), responseErrorText));
    }

    private static Stream<Arguments> submitIncorrectParameters() {
        Faker fakerValue = new Faker();
        return Stream.of(
                Arguments.of(LOGIN.getValue(), "", MISSING_PASSWORD.getValue()),
                Arguments.of("", fakerValue.artist().name(), MISSING_EMAIL_OR_USERNAME.getValue()),
                Arguments.of(fakerValue.internet().emailAddress(), fakerValue.artist().name(), ONLY_DEFINED.getValue()));
    }

}
