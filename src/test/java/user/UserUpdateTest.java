package user;

import client.UserClient;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest extends UserClient {
    private static final String expectedNewEmail = "mikinewemailtest088011@yandex.ru";

    @Test
    @DisplayName("Update user by authorization")
    public void updateUserByAuthorization() {
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
        ValidatableResponse response = updateUserLogin();
        response
                .assertThat()
                .body("success",equalTo(true))
                .body("user.email",equalTo(expectedNewEmail))
                .log().all();

        cleanUpdaterUser();
    }

    @Test
    @DisplayName("Update user without authorization")
    public void updateUserWithoutAuthorization() {
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
        ValidatableResponse response = updateUserLogout();
        response
                .assertThat()
                .statusCode(401)
                .body("success",equalTo(false))
                .body("message",equalTo("You should be authorised"))
                .log().all();
    }
}
