package serenity.zipcode_app;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;


@SerenityTest
public class ZipToCityEnpointTest {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://api.zippopotam.us";
//
    }
    @AfterAll
    public static void tearDown(){
        SerenityRest.clear();
        RestAssured.reset();
    }


    @DisplayName("Testing 1 zip code and get the result")
    @Test
    public void test1ZipCode(){

        given()
                .pathParam("country","us")
                .pathParam("zipcode","22030").

                when()
                .get("/{country}/{zipcode}").prettyPeek().

        then()
                .statusCode(200)
                .body("'post code'", is("22030"))
                .body("places[0].'place name'", is("Fairfax")); // single quote ' if is space between two words in double quote

    }


    @DisplayName("Testing multiple zip codes and get the result")
    @ParameterizedTest // if we have multiple test we use parametrized test, moras googlati da bi bolje znao ovo sranje parametrized
    @ValueSource(strings = {"22030","22031","22032","22033","22034"})
    public void testZipCode(String zip){

        // run this parametrized test with 5 zipcodes of your choice
        //start with no external file
        // then add external csv file in separate test

        System.out.println("zip = " + zip);

        given()
                .pathParam("country","us")
                .pathParam("zipcode",zip).

        when()
                .get("/{country}/{zipcode}");

        Ensure.that("we got successful result",
                v -> v.statusCode(200));

    }

    /**
     * {index} --> represent iteration number (ovo index se pojavi kad misom stanemo na zagradu @ParamatrizedTest()  )
     *
     * {arguments}  -->
     *
     * {methodPatrameterIndexNumber} -->
     *
     */



    @ParameterizedTest(name = "Iteration number {index} : {arguments}")
    @ValueSource(strings = {"22030","22031","22032","22033","22034"})
    public void testDisplayNameManipulation(String zip) {

        /*
Iteration number 1 : 22030
Iteration number 2 : 22031
Iteration number 3 : 22032
Iteration number 4 : 22033
Iteration number 5 : 22034
         */
        // ovako se pojavi kad run program, pise "iteration number 1 : 22030... itd,  a ne pise kao gornjem testu samo [1] 22030, [2] 22031 ...
        // to je zbog @parametrizedTest(name = "Iteration number {index} : {arguments}")


    }


    @ParameterizedTest(name = "Iteration number {index}, Country is {0}, ZipCode is {1}") // ovo {0} i {1} su ustvari 2 argumenta u zagradi odm,a dole u nazivu methode -->  public void testCountryZip(String country, int zip), znaci String country is {0} , int zip je {1}
    @CsvFileSource(resources = "/country_zip.csv",numLinesToSkip = 1)
    public void testCountryZip(String country, int zip){
        // morao sam da obrisem Target folder da bi radio ovaj test na pocetku prvo



        given()
                .pathParam("country",country)
                .pathParam("zipcode",zip).
        when()
                .get("/{country}/{zipcode}") ;

        Ensure.that("we got successful result ", v -> v.statusCode(200) ) ;


    }





}
