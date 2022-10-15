package user;

import client.UserClient;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserCreateTest extends UserClient {

    @Test
    @DisplayName("User create by random credentials")
    public void userRandomCreate(){
        ValidatableResponse randomUser = createUser(
                User.getRandomUser()
        );
        randomUser
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("User create by valid credentials")
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