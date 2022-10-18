package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTest{

    UserClient userClient = new UserClient();

    @Test
    @DisplayName("Update user by authorization")
    public void updateUserByAuthorization() {
        User user = User.getRandomUser();
        userClient.createUser(user);

        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        String accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = userClient.updateUserLogin(accessToken, user);
        response
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email",equalTo(response.extract().path("user.email")))
                .log().all();

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Update user without authorization")
    public void updateUserWithoutAuthorization() {

        ValidatableResponse response = userClient.updateUserLogout(User.getRandomUser());
        response
                .assertThat()
                .statusCode(401)
                .body("success",equalTo(false))
                .body("message",equalTo("You should be authorised"))
                .log().all();
    }
}
