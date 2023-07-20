package tests;

import io.qameta.allure.*;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static constans.HttpStatus.NOT_FOUND;
import static responseassertions.AssertionsResponseGetUsersApi.assertGetUser;
import static service.RequestGetUser.sendGetUser;
import static service.RequestGetUser.sendGetRaw;
import static utils.RandomUtils.getRandomUserForId;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Story("Получение пользователя")
@DisplayName("Получение пользователя API GET users/")
@Owner("Aleksey_Astashkin")
public class GetUserTests extends TestBase {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Tag("sanity")
    @DisplayName("Получить рандомного пользователя по id")
    @Description("Позитивный сценарий")
    public void positiveGetUserTest() {
        val randomUserId = getRandomUserForId();
        val response = sendGetUser(randomUserId.getId());
        assertGetUser(response, randomUserId);

    }

    @DisplayName("Запрос с queryParam")
    @Description("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest(name = "[{0}]")
    @ValueSource(strings = {"100", "0", "-1", "-2147483648", "2147483647"})
    public void negativeGetUserTest(int value) {
        val response = sendGetRaw(value);
        assertEquals(response.statusCode(), NOT_FOUND.getCode());
    }

}
