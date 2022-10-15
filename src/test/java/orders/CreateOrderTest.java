package orders;

import client.OrderClient;
import client.UserClient;
import emity.Login;
import emity.Order;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends OrderClient {

    private final String myToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST)).extract().path("accessToken");
    private final UserClient userClient = new UserClient();

    @Test
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
    public void createOrderByAuth(){
        userClient.createAndLoginUser();

        ValidatableResponse response = getOrderResponse(
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
    public void createOrderByAuthAndNotValidHash(){
        userClient.createAndLoginUser();

        ValidatableResponse response = getOrderResponse(
                new Order(List.of("notValidHash", "lol"))
        );
        response
                .assertThat()
                .statusCode(500)
                .log().all();

        userClient.cleanUser();
    }

    @Test
    public void createOrderByAuthAndNullHash(){
        userClient.createAndLoginUser();

        ValidatableResponse response = getOrderResponse(
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
