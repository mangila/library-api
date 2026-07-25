package com.github.mangila.library.account.rest;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;

import com.github.mangila.library.account.rest.model.LoginRequest;
import com.github.mangila.library.account.rest.model.SignupRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AccountRestResourceIT {

  private static final LoginRequest ADMIN_LOGIN_REQUEST =
      new LoginRequest("admin12345", "admin12345");

  @Test
  void should_login() {
    final Response response =
        given()
            .contentType(ContentType.JSON)
            .body(ADMIN_LOGIN_REQUEST)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/login")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .response();

    final Cookie cookie = response.getDetailedCookie("refresh_token");
    assertThat(cookie)
        .isNotNull()
        .extracting(Cookie::getName, Cookie::hasValue, Cookie::getMaxAge, Cookie::hasPath)
        .containsExactly("refresh_token", true, 864000L, true);

    final String body = response.getBody().asString();
    assertThatJson(body)
        .isObject()
        .containsOnlyKeys("tokenType", "accessToken", "expirationInSeconds");
    assertThatJson(body).node("tokenType").isString().asString().isEqualTo("Bearer");
    assertThatJson(body).node("accessToken").isString().isNotBlank();
    assertThatJson(body).node("expirationInSeconds").isIntegralNumber().isEqualTo(900);
  }

  @Test
  void should_me() {
    final String token =
        given()
            .contentType(ContentType.JSON)
            .body(ADMIN_LOGIN_REQUEST)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/login")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getString("accessToken");

    final String response =
        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .log()
            .everything()
            .when()
            .get("/api/v1/accounts/me")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .asString();

    assertThatJson(response).isObject().containsOnlyKeys("account", "jwt", "refreshTokens");
    assertThatJson(response).node("account.username").isEqualTo("admin12345");
    assertThatJson(response)
        .node("account.roles")
        .isArray()
        .hasSize(2)
        .containsExactly("USER", "ADMIN");
    assertThatJson(response).node("account.active").isBoolean().isEqualTo(true);
    assertThatJson(response).node("account.id").isString().asString().isNotEmpty();
    assertThatJson(response).node("jwt").isObject().isNotEmpty();
    assertThatJson(response).node("refreshTokens").isArray().isNotEmpty();
  }

  @Test
  void should_refresh() {
    final Cookie cookie =
        given()
            .contentType(ContentType.JSON)
            .body(ADMIN_LOGIN_REQUEST)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/login")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .response()
            .getDetailedCookie("refresh_token");

    final Response response =
        given()
            .contentType(ContentType.JSON)
            .cookie(cookie)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/refresh")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .response();

    final Cookie newCookie = response.getDetailedCookie("refresh_token");
    assertThat(newCookie)
        .isNotNull()
        .extracting(Cookie::getName, Cookie::hasValue, Cookie::getMaxAge, Cookie::hasPath)
        .containsExactly("refresh_token", true, 864000L, true);

    final String body = response.getBody().asString();
    assertThatJson(body)
        .isObject()
        .containsOnlyKeys("tokenType", "accessToken", "expirationInSeconds");
    assertThatJson(body).node("tokenType").isString().asString().isEqualTo("Bearer");
    assertThatJson(body).node("accessToken").isString().isNotBlank();
    assertThatJson(body).node("expirationInSeconds").isIntegralNumber().isEqualTo(900);
  }

  @Test
  void should_signup() {
    final SignupRequest signupRequest = new SignupRequest("newuser12345", "newpassword12345");

    final String response =
        given()
            .contentType(ContentType.JSON)
            .body(signupRequest)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/signup")
            .then()
            .log()
            .everything()
            .statusCode(201)
            .header("Location", endsWith("/api/v1/accounts/me"))
            .extract()
            .asString();

    assertThatJson(response).isObject().containsOnlyKeys("account");
    assertThatJson(response).node("account.username").isEqualTo("newuser12345");
    assertThatJson(response).node("account.roles").isArray().hasSize(1).containsExactly("USER");
    assertThatJson(response).node("account.active").isBoolean().isTrue();
    assertThatJson(response).node("account.id").isString().asString().isNotEmpty();
  }
}
