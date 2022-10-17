package client;

import config.*;
import emity.*;
import utils.Constants;
import org.apache.commons.lang3.StringUtils;

import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class UserClient extends Constants {

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
        String newEmail = "updatetestemail23033@yandex.ru";

        ValidatableResponse getToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        User changeUser = new User(newEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
                .body(changeUser)
                .patch(API_INFO)
                .then()
                .log().all();
    }

    public ValidatableResponse updateUserLogout(){
        String newEmail = "updatetestemail23033@yandex.ru";

        User changeUser = new User(newEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .body(changeUser)
                .patch(API_INFO)
                .then()
                .log().all();
    }

    public void cleanUpdaterUser(){
        String expectedNewEmail = "updatetestemail23033@yandex.ru";

        ValidatableResponse getToken = loginUser(new Login(expectedNewEmail,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
                .when()
                .delete(API_INFO);
    }
}
