package api.tests;

import api.models.CreateUsersBodyModel;
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

import static api.data.TestDataValues.parameterizedNegativeData;
import static api.responseassertions.AssertionsResponseUpdateUsers.assertUpdateUserTestApi;
import static api.service.RequestUpdateUser.patchUpdateUser;

@Story("Изменение пользователя")
@DisplayName("Изменение пользователя API PUT /users/id")
public class PatchUpdateUsersTests extends TestBase {

    CreateUsersBodyModel body = new CreateUsersBodyModel();
    static Faker faker = new Faker();

    @Test
    @Tag("sanity")
    @DisplayName("Изменение пользователя")
    @Description("ПозитивнВ сценарий")
    public void positiveUpdateUserTest() {
        body.setName(faker.name().firstName()).setJob(faker.artist().name());
        val response = patchUpdateUser(body, faker.random().nextInt(1, 100));
        assertUpdateUserTestApi(response, body);

    }

    @DisplayName("Негативный сценарий изменения пользователя ")
    @Description("Негативный сценарии")
    @ParameterizedTest(name = "[Name = {0}, Job = {1}]")
    @MethodSource("checkOutputParamsForPage")
    public void negativeUpdateUserTests(String valueName, String valueJob) {
        body.setName(valueName).setJob(valueJob);
        val response = patchUpdateUser(body, faker.random().nextInt(1, 100));
        assertUpdateUserTestApi(response, body);
    }

    private static Stream<Arguments> checkOutputParamsForPage() {
        return parameterizedNegativeData();
    }

}
