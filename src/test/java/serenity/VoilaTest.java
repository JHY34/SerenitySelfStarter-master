package serenity;

import net.serenitybdd.junit5.SerenityTest;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import serenity.steps.B20Actions;

@SerenityTest
@Tag("Voila")
public class VoilaTest {

    // mvn clean verify -Dtags="Voila"
    // this is the direction to RUn only specific test on Terminal

    @Steps
    B20Actions newUser;



    @Test
    public void testVoila(){

        //B20USER preparedSomething

        //B20USER takeAnAction

        //B20USER expectSomeResult

        //GIVEN
        newUser.preparedSomething();
        //WHEN
        newUser.takeAnAction();
        //THEN
        newUser.expectSomeResult();



    }



}