package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import utils.Constants;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest extends Constants {

    UserClient userClient = new UserClient();
    private final String expectedNewEmail = "updatetestemail23033@yandex.ru";

    @Test
    @DisplayName("Update user by authorization")
    public void updateUserByAuthorization() {
        User user = User.getRandomUser();
        userClient.createUser(user);

        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        String accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = userClient.updateUserLogin(accessToken, expectedNewEmail);
        response
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email",equalTo(expectedNewEmail))
                .log().all();

        userClient.cleanUpdaterUser(accessToken);
    }

    @Test
    @DisplayName("Update user without authorization")
    public void updateUserWithoutAuthorization() {
        ValidatableResponse response = userClient.updateUserLogout(expectedNewEmail);
        response
                .assertThat()
                .statusCode(401)
                .body("success",equalTo(false))
                .body("message",equalTo("You should be authorised"))
                .log().all();
    }
}
