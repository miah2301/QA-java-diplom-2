package orders;

import client.OrderClient;
import client.UserClient;
import emity.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends OrderClient {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Create order without authorization")
    public void createOrderWithoutAuth(){
        ValidatableResponse response = getOrderResponse(
                new Order(List.of(validHashOne, validHashTwo))
        );
        response
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Test
    @DisplayName("Create order without authorization and not valid hash")
    public void createOrderWithoutAuthAndNotValidHash(){
        ValidatableResponse response = getOrderResponse(
                new Order(List.of("notValidHash", "lol"))
        );
        response
                .assertThat()
                .statusCode(500)
                .log().all();
    }

    @Test
    @DisplayName("Create order without authorization and null hash")
    public void createOrderWithoutAuthAndNullHash(){
        ValidatableResponse response = getOrderResponse(
                new Order(null)
        );
        response
                .assertThat()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Create order by authorization")
    public void createOrderByAuth(){
        ValidatableResponse response = getOrderResponseLogin(
                new Order(List.of(validHashOne, validHashTwo))
        );
        response
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();

        userClient.cleanUser();
    }

    @Test
    @DisplayName("Create order by authorization and not valid hash")
    public void createOrderByAuthAndNotValidHash(){
        ValidatableResponse response = getOrderResponseLogin(
                new Order(List.of("notValidHash", "lol"))
        );
        response
                .assertThat()
                .statusCode(500)
                .log().all();

        userClient.cleanUser();
    }

    @Test
    @DisplayName("Create order by authorization and null hash")
    public void createOrderByAuthAndNullHash(){
        ValidatableResponse response = getOrderResponseLogin(
                new Order(null)
        );
        response
                .assertThat()
                .statusCode(400)
                .body("success", equalTo(false))
                .log().all();

        userClient.cleanUser();
    }
}
