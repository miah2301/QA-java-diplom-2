package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import config.*;
import emity.*;
import utils.*;
import client.*;

import static io.restassured.http.ContentType.JSON;

public class Config extends Constants {

    public String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(JSON)
                .build();
    }
}
