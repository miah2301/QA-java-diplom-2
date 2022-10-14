package client;

import config.*;
import emity.*;
import io.restassured.response.ValidatableResponse;
import utils.*;
import client.*;

import static io.restassured.RestAssured.given;

public class OrderClient extends Config {

    public ValidatableResponse getOrderResponse(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(API_ORDERS)
                .then();
    }
}
