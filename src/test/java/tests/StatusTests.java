package tests;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

@DisplayName("Тесты по ручке GET /status")
public class StatusTests extends TestBase {

    @DisplayName("Тест проверки \"total\", без логирования")
    @Test
    public void totalAmountTest() {
        get(statusPath)
                .then()
                .body("total", is(5));
    }

    @DisplayName("Тест проверки \"total\", с логированием ответа")
    @Test
    public void totalAmountTest_withResponseLogs() {
        get(statusPath)
                .then()
                .log().all()
                .body("total", is(5));
    }

    @DisplayName("Тест проверки \"total\", с логированием запроса и ответа")
    @Test
    public void totalAmountTest_withAllLogs() {
        given()
                .log().all()
//                .log().uri()
//                .log().method()
//                .log().headers()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .body("total", is(5));
    }

    @DisplayName("Тест статус-кода ответа 200")
    @Test
    public void status200Test() {
        given()
                .log().all()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .statusCode(200);
    }

    @DisplayName("Тест присутствия в ответе всех необходимых параметров")
    @Test
    public void requiredKeysTest() {
        given()
                .log().all()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("total"))
                .body("", hasKey("used"))
                .body("", hasKey("queued"))
                .body("", hasKey("pending"))
                .body("", hasKey("browsers"));
    }

    @DisplayName("Тест версии браузера \"Chrome\"")
    @Test
    public void chromeVersionsTest() {
        given()
                .log().all()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.chrome", hasKey("128.0"));
    }

    @DisplayName("Тест присутствия в ответе всех необходимых параметров по схеме")
    @Test
    public void statusSchemaTest() {
        given()
                .log().all()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));
    }

    @DisplayName("Полный тест проверки \"total\"")
    @Test
    public void bestTotalAmountTest() {
        given()
                .log().all()
                .when()
                .get(statusPath)
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"))
                .body("total", is(5));
    }
}