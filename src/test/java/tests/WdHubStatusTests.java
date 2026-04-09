package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты по ручке GET /wd/hub/status")
public class WdHubStatusTests extends TestBase {

    @DisplayName("Тест статус-кода ответа 200")
    @Test
    public void statusCode200Test() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get(wdHubStatusPath)
                .then()
                .log().all()
                .statusCode(200);
    }

    @DisplayName("Тест статус-кода ответа 404 по ломаной ручке")
    @Test
    public void statusCode404Test() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd")
                .then()
                .log().all()
                .statusCode(404);
    }

    @DisplayName("Тест статус-кода ответа 401 для неавторизованного пользователя")
    @Test
    public void unauthorizedStatusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get(wdHubStatusPath)
                .then()
                .log().all()
                .statusCode(401);
    }

    @DisplayName("Тест статус-кода ответа 401 с невалидным паролем")
    @Test
    public void wrongPasswordTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "user1")
                .when()
                .get(wdHubStatusPath)
                .then()
                .log().all()
                .statusCode(401);
    }

    @DisplayName("Тест значения параметра \"message\"")
    @Test
    public void valueMessageTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get(wdHubStatusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/WdHubStatus_response_schema.json"))
                .body("value.message", containsString("Selenoid 1.11.3 built at"));
    }

    @DisplayName("Тест значения параметра \"ready\"")
    @Test
    public void valueReadyTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get(wdHubStatusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/WdHubStatus_response_schema.json"))
                .body("value.ready", is(true));
    }
}