package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserDeleteTest extends UserClient{
    private final String myToken = loginUser(new Login(EMAIL_TEST,PASSWORD_TEST)).extract().path("accessToken");

    @DisplayName("Checking user deletion")
    @Test
    public void deleteUserTest(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse deleteAcc = deleteUser(StringUtils.substringAfter(myToken, " "));
        deleteAcc
                .assertThat()
                .body("message", equalTo("User successfully removed"))
                .log().all();
    }
}
