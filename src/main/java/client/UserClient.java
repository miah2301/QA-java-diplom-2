package client;

import config.*;
import emity.*;
import org.apache.commons.lang3.StringUtils;

import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class UserClient extends Config {

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .post(API_CREATE_USER)
                .then()
                .log().all();
    }

    public void deleteUser(String accessToken){
        given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .delete(API_INFO)
                .then()
                .log().all();
    }

    public ValidatableResponse loginUser(Login login){
        return given()
                .spec(getBaseSpec())
                .body(login)
                .post(API_AUTHORIZATION_USER)
                .then()
                .log().all();
    }

    public ValidatableResponse updateUserLogin(){
        String newEmail = "mikinewemailtest088011@yandex.ru";

        ValidatableResponse getToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        User changeUser = new User(newEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
                .body(changeUser)
                .patch(API_INFO)
                .then();
    }

    public ValidatableResponse updateUserLogout(){
        String newEmail = "mikinewemailtest088011@yandex.ru";

        User changeUser = new User(newEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .body(changeUser)
                .patch(API_INFO)
                .then();
    }

    public void cleanUser(){
        ValidatableResponse getToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        deleteUser(StringUtils.substringAfter(accessToken, " "));
    }

    public void cleanUpdaterUser(){
        String expectedNewEmail = "mikinewemailtest088011@yandex.ru";

        ValidatableResponse getToken = loginUser(new Login(expectedNewEmail,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
                .when()
                .delete(API_INFO);
    }

    public void createAndLoginUser(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
        loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
    }
}
