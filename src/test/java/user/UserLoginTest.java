package user;

import client.UserClient;
import com.github.javafaker.Code;
import emity.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import utils.Constants;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends Constants {

    UserClient userClient = new UserClient();

    @Test
    @DisplayName("User logout by valid credentials")
    public void userLoginByValidCredentials(){
        userClient.createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse response = userClient.loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
            response
                    .assertThat()
                     .statusCode(200)
                    .log().all()
                    .body("success", equalTo(true))
                    .log().all();

        userClient.cleanUser();
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
