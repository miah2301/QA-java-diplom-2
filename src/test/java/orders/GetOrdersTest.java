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

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest extends Constants {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    @Before
    public void setUp(){
        userClient.createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
    }

    @Test
    @DisplayName("Get order without authorization user")
    public void getOrderWithoutAuthorizationUser(){
        ValidatableResponse allOrders = orderClient.getAllOrdersLogoutUser();
        allOrders
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo( "You should be authorised"))
                .log().all();
    }

    @Test
    @DisplayName("Get order authorization user")
    public void getAllOrdersAuthUserTest(){
        ValidatableResponse getToken = userClient.loginUser(new Login(EMAIL_TEST, PASSWORD_TEST));
        String accessToken = StringUtils.substringAfter(getToken.extract().path("accessToken"), " ");

        ValidatableResponse allOrders = orderClient.getAllOrdersLoginUser(accessToken);
        allOrders
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @After
    public void tearDown(){
        userClient.cleanUser();
    }
}
