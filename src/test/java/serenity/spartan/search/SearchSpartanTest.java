package serenity.spartan.search;

import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serenity.utility.SpartanTestBase;

import static net.serenitybdd.rest.SerenityRest.*;

public class SearchSpartanTest extends SpartanTestBase {

    @DisplayName("Authenticated user should be able to search")
    @Test
    public void testSearch() {

        given()
                .auth().basic("admin" , "admin")
                .queryParam("nameContains" , "a").
        when()
                .get("/spartans");

        Ensure.that("Request was successful" ,
                vRes -> vRes.statusCode(200))
                ;
        // chain above ensure you got json result
        // open another ensure
        // make sure you got all names contains a

       // Ensure.that("")

    }


}
