package serenity.utility;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SpartanTestBase {

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "http://34.203.40.168:8000";
        RestAssured.basePath = "/api";

    }

    @AfterAll
    public static void cleanUp() {
        SerenityRest.clear();
        RestAssured.reset();
    }

}
