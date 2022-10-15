package user;

import client.UserClient;
import emity.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends UserClient {

    @Test
    @DisplayName("User logout by valid credentials")
    public void userLoginByValidCredentials(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse response = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
            response
                    .assertThat()
                     .statusCode(200)
                    .log().all()
                    .body("success", equalTo(true))
                    .log().all();

        cleanUser();
    }

    @Test
    @DisplayName("User login is empty email")
    public void userLoginByEmptyEmail(){
        ValidatableResponse response = loginUser(new Login(null,PASSWORD_TEST));
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
        ValidatableResponse response = loginUser(new Login(EMAIL_TEST,null));
        response
                .assertThat()
                .statusCode(401)
                .log().all()
                .body("success", equalTo(false))
                .log().all();
    }
}
