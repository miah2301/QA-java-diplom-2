package client;

import emity.*;
import utils.Constants;

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

    public ValidatableResponse updateUserLogin(String accessToken, String newEmail){
        User changeUser = new User(newEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(changeUser)
                .patch(API_INFO)
                .then()
                .log().all();
    }

    public ValidatableResponse updateUserLogout(String updateEmail){
        User changeUser = new User(updateEmail, PASSWORD_TEST, NAME_TEST);
        return given()
                .spec(getBaseSpec())
                .body(changeUser)
                .patch(API_INFO)
                .then()
                .log().all();
    }

    public void cleanUpdaterUser(String accessToken){
        given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(API_INFO)
                .then()
                .log().all();
    }
}
