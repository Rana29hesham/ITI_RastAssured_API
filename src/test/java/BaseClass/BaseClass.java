package BaseClass;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseClass {

    @BeforeClass
    public RequestSpecification Request(){
        RequestSpecification requestSpecification = given()
                .baseUri("https://fakestoreapi.com/");
        return requestSpecification;
    }

}
