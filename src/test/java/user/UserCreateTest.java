package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Constants;

import static org.hamcrest.Matchers.equalTo;

public class UserCreateTest extends Constants {

    UserClient userClient = new UserClient();
    private String accessToken;
    private User user;

    @Before
    public void setUp(){

    }

    @Test
    @DisplayName("User create by random credentials")
    public void userRandomCreate(){
        ValidatableResponse randomUser = userClient.createUser(
                User.getRandomUser());
        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        randomUser
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("User create by valid credentials")
    public void userCreateByValidCredentials(){
        user = User.getRandomUser();
        userClient.createUser(user);

        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = userClient.createUser(user);
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .log().all();

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("User create is empty email")
    public void userCreateIsEmptyEmail(){
        ValidatableResponse response = userClient.createUser(new User(null,PASSWORD_TEST,NAME_TEST));
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .log().all();
    }
    @Test
    @DisplayName("User create is empty password")
    public void userCreateIsEmptyPassword(){
        ValidatableResponse response = userClient.createUser(new User(EMAIL_TEST,null,NAME_TEST));
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .log().all();
    }

    @Test
    @DisplayName("User create is empty name")
    public void userCreateIsEmptyName(){
        ValidatableResponse response = userClient.createUser(new User(EMAIL_TEST,PASSWORD_TEST,null));
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .log().all();
    }
}