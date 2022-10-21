package client;

import config.Config;
import emity.*;
import utils.Constants;

import io.restassured.response.ValidatableResponse;

import static config.Config.getBaseSpec;
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

    public ValidatableResponse updateUserLogin(String accessToken, User user){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(user)
                .patch(API_INFO)
                .then()
                .log().all();
    }

    public ValidatableResponse updateUserLogout(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .patch(API_INFO)
                .then()
                .log().all();
    }
}
