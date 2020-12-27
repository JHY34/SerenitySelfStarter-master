package serenity.library_app;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serenity.utility.ConfigReader;
import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.*;

@SerenityTest
public class LibraryTest {

    private EnvironmentVariables environmentVariables ;

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://library1.cybertekschool.com";
        RestAssured.basePath = "/rest/v1" ;
    }

    @AfterAll
    public static void tearDown(){
        RestAssured.reset();
        SerenityRest.clear();
    }

    @Test
    public void getTokenTest(){
//        given()
//                .log().all()
//                .contentType( ContentType.URLENC  )
//                .formParam("email", "librarian69@library")
//                .formParam("password","KNPXrm3S").
//        when()
//                .post("/login") ;
        ConfigReader configReader = new ConfigReader();
        System.out.println("configReader.getProperty(\"base.url\") = "
                + configReader.getProperty("base.url"));
//        System.out.println(EnvironmentSpecificConfiguration.from(environmentVariables)
//                .getProperty("base.url"));
    }
}