package user;

import client.UserClient;
import emity.*;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends UserClient {

    @Test
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
