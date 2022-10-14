package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class UserDeleteTest extends UserClient {

/*    @Test
    public void deleteUser(){
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));

        ValidatableResponse response = loginUser(new Login(EMAIL_TEST, PASSWORD_TEST));
        String accessToken = response.extract().path("accessToken");

        deleteUser(StringUtils.substringAfter(accessToken, " "));
    }*/
}
