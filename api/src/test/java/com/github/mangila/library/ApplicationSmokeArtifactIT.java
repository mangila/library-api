package com.github.mangila.library;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
class ApplicationSmokeArtifactIT {

  @Test
  void smoke() {
    given().when().get("/q/health/ready").then().statusCode(200);
  }
}
