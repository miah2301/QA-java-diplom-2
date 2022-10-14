package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import config.*;
import emity.*;
import utils.*;
import client.*;

import static io.restassured.http.ContentType.JSON;

public class Config extends Constants {
    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .build();
    }
}
