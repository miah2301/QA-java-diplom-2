package user;

import client.UserClient;
import emity.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import utils.Constants;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends Constants {

    UserClient userClient = new UserClient();

    @Test
    @DisplayName("User logout by valid credentials")
    public void userLoginByValidCredentials(){
        User user = User.getRandomUser();
        userClient.createUser(user);

        ValidatableResponse response = userClient.loginUser(Login.from(user));
        String accessToken = StringUtils.substringAfter(response.extract().path("accessToken"), " ");

            response
                    .assertThat()
                     .statusCode(200)
                    .log().all()
                    .body("success", equalTo(true))
                    .log().all();

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("User login is empty email")
    public void userLoginByEmptyEmail(){
        ValidatableResponse response = userClient.loginUser(new Login(null,PASSWORD_TEST));
        response
                .assertThat()
                .statusCode(401)
                .log().all()
                .body("success", equalTo(false))
                .log().all();
    }

    @Test
    @DisplayName("User login is empty password")
    public void userLoginByEmptyPassword(){
        ValidatableResponse response = userClient.loginUser(new Login(EMAIL_TEST,null));
        response
                .assertThat()
                .statusCode(401)
                .log().all()
                .body("success", equalTo(false))
                .log().all();
    }
}
