package user;

import client.UserClient;
import emity.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {
    UserClient userClient = new UserClient();
    private User user;

    @Before
    public void setUp(){
        user = User.getRandomUser();
    }
    @Test
    @DisplayName("User logout by valid credentials")
    public void userLoginByValidCredentials(){
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
        user.setEmail(null);

        ValidatableResponse response = userClient.loginUser(Login.from(user));
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
        user.setPassword(null);

        ValidatableResponse response = userClient.loginUser(Login.from(user));
        response
                .assertThat()
                .statusCode(401)
                .log().all()
                .body("success", equalTo(false))
                .log().all();
    }
}
