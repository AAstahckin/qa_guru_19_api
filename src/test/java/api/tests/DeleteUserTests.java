package api.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static api.constans.Urls.URL_USER;
import static api.service.Requests.sendDeleteUserRequest;

@DisplayName("Удаление пользователя API DELETE users/")
public class DeleteUserTests extends TestBase {

    @DisplayName("Удаление пользователя с id")
    @Description("Позитивный сценарий")
    @ParameterizedTest(name = " = {0}]")
    @ValueSource(strings = {"1", "1000", "10000", "test"})
    public void deleteUsersTests(String userId) {
        sendDeleteUserRequest(URL_USER.getUrl(), userId);
    }
}
