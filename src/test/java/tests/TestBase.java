package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    static String statusPath = "/status";
    static String wdHubStatusPath = "/wd/hub/status";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://selenoid.autotests.cloud";
    }
}
