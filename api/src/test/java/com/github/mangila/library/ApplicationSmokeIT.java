package com.github.mangila.library;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ApplicationSmokeIT {

  @Test
  void smoke() {
    given()
        .when()
        .log()
        .everything()
        .get("/q/health/ready")
        .then()
        .log()
        .everything()
        .statusCode(200);
  }
}
