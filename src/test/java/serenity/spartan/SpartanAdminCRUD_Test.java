package serenity.spartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.DisplayName.class)
@SerenityTest
public class SpartanAdminCRUD_Test {

    //    private static RequestSpecification adminSpec ;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.158.11.99:8000";
        RestAssured.basePath = "/api";

        // this is setting static field of rest assured class requestSpecification
        // to the value we specified,
        // so it become global evert test in this case
        // adminSpec = given().auth().basic("admin","admin") ;
    }
    @AfterAll
    public static void cleanUp(){
        reset();
    }
    @DisplayName("1.Admin User Should be able to Add Spartan")
    @Test
    public void testAdd1Data(){
        Map<String,Object> payload = SpartanUtil.getRandomSpartanRequestPayload();
        given()
                .log().body()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(payload).
                when()
                .post("/spartans") ;
        Ensure.that("Request was successful"  ,
                thenResponse -> thenResponse.statusCode(201) )
                .andThat("We got json format result",
                        thenResponse -> thenResponse.contentType(ContentType.JSON) )
                .andThat("success message is A Spartan is born!" ,
                        thenResponse ->
                                thenResponse.body("success",is("A Spartan is Born!") )  )
        ;
        Ensure.that("The data " + payload + " we provided added correctly",
                vRes -> vRes.body("data.name", is( payload.get("name")  ) )
                        .body("data.gender", is( payload.get("gender")  ) )
                        .body("data.phone", is( payload.get("phone")  ) ) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue() )))    ) ;
        // how do we extract information after sending requests ? :
        // for example I want to print out ID
        // lastResponse() method is coming SerenityRest class
        // and return the Response Object obtained from last ran request.
//        lastResponse().prettyPeek();
        System.out.println("lastResponse().jsonPath().getInt(\"data.id\") = "
                + lastResponse().jsonPath().getInt("data.id"));
    }
    @DisplayName("2.Admin Should be able to read single data")
    @Test
    public void getOneData(){

        int newID = lastResponse().jsonPath().getInt("data.id") ;
        System.out.println("newID = " + newID);

        when().get("/spartans/{id}" , newID);

        Ensure.that("We can access newly generated data",
                thenResponse-> thenResponse.statusCode(200) ) ;

        // add all validation for body here as homework
        // add put and patch as homework
    }

    @DisplayName("3.Admin Should be able to delete single data")
    @Test
    public void testDeleteOneData () {

        // capture the id from the last request
        int myId = lastResponse().jsonPath().getInt("data.id") ;

        given()
                .auth().basic("admin" , "admin")
                .pathParam("id" , myId).
        when()
                .delete("/spartans/{id}");

        Ensure.that("Request is successful" ,
                thenResponse -> thenResponse.statusCode(204)) ;

        // send another get request to makes sure you get 404
        given()
                .auth().basic("admin" , "admin")
                .pathParam("id" , myId).
                when()
                .delete("/spartans/{id}");

        Ensure.that("Delete was successful, Can not find data anymore" ,
                thenResponse -> thenResponse.statusCode(404)) ;

    }


}
