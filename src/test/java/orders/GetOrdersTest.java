package orders;

import client.OrderClient;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest extends OrderClient{

    @DisplayName("Get order without authorization user")
    @Test
    public void getOrderWithoutAuthorizationUser(){
        ValidatableResponse allOrders = getAllOrdersLogoutUser();
        allOrders
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "You should be authorised"))
                .log().all();
    }

    @DisplayName("Get order authorization user")
    @Test
    public void getAllOrdersAuthUserTest(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse allOrders = getAllOrdersLoginUser();
        allOrders
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();

        cleanUser();
    }
}
