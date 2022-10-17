package client;

import emity.*;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import utils.Constants;

import static io.restassured.RestAssured.given;

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