package serenity.zipcode_app;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.serenitybdd.rest.SerenityRest.*;

@SerenityTest
public class ZipToCityEndpointTest {

    // https://api.zippopotam.us/{{country}}/{{zipcode}}

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://api.zippopotam.us";
    }

    @AfterAll
    public static void cleanUp() {
        reset();
    }

    @DisplayName("Testing 1 zip code and get the result")
    @Test
    public void test1ZipCode(){
        given()
                .pathParam("country","us")
                .pathParam("zipcode","22030").
        when()
                .get("/{country}/{zipcode}").prettyPeek()
                ;
    }



}
