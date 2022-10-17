package orders;

import client.OrderClient;
import client.UserClient;
import emity.Login;
import emity.Order;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Constants;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends Constants {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        user = User.getRandomUser();
        userClient.createUser(user);
    }

    @Test
    @DisplayName("Create order without authorization")
    public void createOrderWithoutAuth(){
        ValidatableResponse response = orderClient.getOrderResponse(
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
        ValidatableResponse response = orderClient.getOrderResponse(
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
        ValidatableResponse response = orderClient.getOrderResponse(
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
        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = orderClient.getOrderResponseLogin(
                new Order(List.of(validHashOne, validHashTwo)), accessToken);
        response
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Test
    @DisplayName("Create order by authorization and not valid hash")
    public void createOrderByAuthAndNotValidHash(){
        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = orderClient.getOrderResponseLogin(
                new Order(List.of("notValidHash", "lol")), accessToken);
        response
                .assertThat()
                .statusCode(500)
                .log().all();
    }

    @Test
    @DisplayName("Create order by authorization and null hash")
    public void createOrderByAuthAndNullHash(){
        ValidatableResponse getToken = userClient.loginUser(Login.from(user));
        accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse response = orderClient.getOrderResponseLogin(
                new Order(null), accessToken);
        response
                .assertThat()
                .statusCode(400)
                .body("success", equalTo(false))
                .log().all();
    }

    @After
    public void tearDown(){
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
