package serenity.spartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;
//import static io.restassured.RestAssured.* ;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// DO NOT
// import static io.restassured.RestAssured.* ;
// DO IMPORT THIS ONE
import static net.serenitybdd.rest.SerenityRest.*;

@SerenityTest
public class SimpleSpartanTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.158.11.99:8000";
        RestAssured.basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Testing GET /api/hello Endpoint")
    @Test
    public void testingHelloEndPoint(){

        when()
                .get("/hello").
                then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body( is("Hello from Sparta") )
        ;
        //        Serenity's way of generating some steps for verification
        // in the report using Ensure class
        Ensure.that("Make sure endpoint works " ,
                response -> response
                        .statusCode(200)
                        .contentType(ContentType.TEXT)
                        .body( is("Hello from Sparta") )
        );

        Ensure.that("Success response was received",
                thenResponse -> thenResponse.statusCode(200) )
                .andThat("I got text response" ,
                        blaResponse -> blaResponse.contentType(ContentType.TEXT) )
                .andThat("I got Hello from Sparta" ,
                        vResponse -> vResponse.body(is("Hello from Sparta")))
                .andThat("I got my response within 2 seconds",
                        vResponse -> vResponse.time( lessThan(1L), TimeUnit.SECONDS  ) )
        ;

    }

    @DisplayName("Admin User should be able to Add Spartan")
    @Test
    public void testAdd1Data () {
        Map<String, Object> payload = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .log().all()
                .auth().basic("admin" , "admin")
                .contentType(ContentType.JSON)
                .body(payload).
        when()
                .post("/spartans") ;
        Ensure.that("Request was successful"  ,
                thenResponse -> thenResponse.statusCode(201) )
        .andThat("We got json format result" ,
                thenResponse -> thenResponse.contentType(ContentType.JSON))
        .andThat("success message is A Spartan is born!" ,
                thenResponse -> thenResponse.body("success" , is("A Spartan is Born!")))
        ;

        Ensure.that("The data we provided added correctly" ,
                vRes -> vRes.body("data.name" , is(payload.get("name")))
                        .body("data.gender" , is(payload.get("gender")))
                        .body("data.phone" , is(payload.get("phone"))) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue()))))
                ;

        // how do we extract information after sending request ?
        // for example I want to print out ID
        // lastResponse() method is coming SerenityRest class
        // and return the Response Object obtained from last ran request
        // lastResponse().prettyPeek()

       // lastResponse().prettyPeek();  <-- PRINTS

        System.out.println("lastResponse().jsonPath().getInt(\"data.id\" ) = " + lastResponse().jsonPath().getInt("data.id"));
        // prints the id number of the last Response


    }


}
