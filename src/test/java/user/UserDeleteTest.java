package user;

import client.UserClient;
import emity.Login;
import emity.User;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class UserDeleteTest extends UserClient {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        createUser(new User(EMAIL_TEST,PASSWORD_TEST,NAME_TEST));
    }

    @Test
    public void deleteUser(){
        ValidatableResponse response = loginUser(new Login(EMAIL_TEST, PASSWORD_TEST));
        String accessToken = response.extract().path("accessToken");
        deleteUser(StringUtils.substringAfter(accessToken, " "));
    }
}
