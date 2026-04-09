package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static final String statusPath = "/status";
    protected static final String wdHubStatusPath = "/wd/hub/status";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://selenoid.autotests.cloud";
    }
}
