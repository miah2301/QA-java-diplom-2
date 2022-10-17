package user;

import client.UserClient;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import utils.Constants;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest extends Constants {

    UserClient userClient = new UserClient();
    private static final String expectedNewEmail = "updatetestemail23033@yandex.ru";

    @Test
    @DisplayName("Update user by authorization")
    public void updateUserByAuthorization() {
        userClient.createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse response = userClient.updateUserLogin();
        response
                .assertThat()
                .body("success",equalTo(true))
                .body("user.email",equalTo(expectedNewEmail))
                .log().all();

        userClient.cleanUpdaterUser();
    }

    @Test
    @DisplayName("Update user without authorization")
    public void updateUserWithoutAuthorization() {
        ValidatableResponse response = userClient.updateUserLogout();
        response
                .assertThat()
                .statusCode(401)
                .body("success",equalTo(false))
                .body("message",equalTo("You should be authorised"))
                .log().all();
    }
}
