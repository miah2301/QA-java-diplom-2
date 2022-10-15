package client;

import emity.*;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.RestAssured.given;

public class OrderClient extends UserClient {

    public ValidatableResponse getOrderResponse(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .post(API_ORDERS)
                .then()
                .log().all();
    }

    public ValidatableResponse getOrderResponseLogin(Order order) {
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse getToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
                .body(order)
                .post(API_ORDERS)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrdersLoginUser() {

        ValidatableResponse getToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST));
        String accessToken = getToken.extract().path("accessToken");

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(StringUtils.substringAfter(accessToken, " "))
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