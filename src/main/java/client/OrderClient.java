package client;

import emity.*;
import io.restassured.response.ValidatableResponse;
import constants.Constants;

import static io.restassured.RestAssured.given;
import static config.Config.getBaseSpec;

public class OrderClient extends Constants {

    public ValidatableResponse getOrderResponse(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .post(API_ORDERS)
                .then()
                .log().all();
    }

    public ValidatableResponse getOrderResponseLogin(Order order, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(order)
                .post(API_ORDERS)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrdersLoginUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .get(API_ORDERS)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrdersLogoutUser() {
        return given()
                .spec(getBaseSpec())
                .get(API_ORDERS)
                .then()
                .log().all();
    }
}