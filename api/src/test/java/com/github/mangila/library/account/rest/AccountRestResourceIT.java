package com.github.mangila.library.account.rest;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.endsWith;

import com.github.mangila.library.account.rest.model.LoginRequest;
import com.github.mangila.library.account.rest.model.RefreshRequest;
import com.github.mangila.library.account.rest.model.SignupRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AccountRestResourceIT {

  private static final LoginRequest ADMIN_LOGIN_REQUEST =
      new LoginRequest("admin12345", "admin12345");

  @Test
  void should_login() {
    final String response =
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
            .asString();

    assertThatJson(response).isObject().containsOnlyKeys("accessToken", "refreshToken");
    assertThatJson(response).node("accessToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.expiresIn").isString().asString().isNotEmpty();
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
            .getString("accessToken.value");

    final String response =
        given()
            .header("Authorization", "Bearer " + token)
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

    assertThatJson(response).isObject().containsOnlyKeys("account", "jwt");
    assertThatJson(response).node("account.username").isEqualTo("admin12345");
    assertThatJson(response).node("account.roles").isArray().contains("ADMIN");
    assertThatJson(response).node("account.active").isEqualTo(true);
    assertThatJson(response).node("account.id").isString().asString().isNotEmpty();
    assertThatJson(response).node("jwt").isObject();
  }

  @Test
  void should_refresh() {
    final String refreshToken =
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
            .getString("refreshToken.value");
    final RefreshRequest refreshRequest =
        new RefreshRequest(ADMIN_LOGIN_REQUEST.username(), refreshToken);

    final String response =
        given()
            .contentType(ContentType.JSON)
            .body(refreshRequest)
            .log()
            .everything()
            .when()
            .post("/api/v1/accounts/refresh")
            .then()
            .log()
            .everything()
            .statusCode(200)
            .extract()
            .asString();

    assertThatJson(response).node("accessToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.expiresIn").isString().asString().isNotEmpty();
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

    assertThatJson(response).node("accessToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.value").isString().asString().isNotEmpty();
    assertThatJson(response).node("refreshToken.expiresIn").isString().asString().isNotEmpty();
  }
}
