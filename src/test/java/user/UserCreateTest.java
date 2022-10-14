package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserCreateTest extends UserClient {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    public void userCreateByValidCredentials(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
        ValidatableResponse response = createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .log().all();
        cleanUser();
    }

    @Test
    @DisplayName("User create is empty email")
    public void userCreateIsEmptyEmail(){
        ValidatableResponse response = createUser(new User(null,PASSWORD_TEST,NAME_TEST));
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
        ValidatableResponse response = createUser(new User(EMAIL_TEST,null,NAME_TEST));
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
        ValidatableResponse response = createUser(new User(EMAIL_TEST,PASSWORD_TEST,null));
        response
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .log().all();
    }
}