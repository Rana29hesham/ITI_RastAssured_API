package ProductsApi_Testcases;
import BaseClass.BaseClass;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TC005_GetCategories  {
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_Categories_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("products/categories")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test(priority = 2 ,description = "2-Check categories name")
    public void TC_Categories_002 (){
       Response response = given()
               .spec(baseClass.Request())
        .when()
                .get("products/categories")
        .then()
               .log().body()
               .extract().response();

        Assert.assertTrue(response.asString().contains("electronics"));
        Assert.assertTrue(response.asString().contains("jewelery"));
        Assert.assertTrue(response.asString().contains("men's clothing"));
        Assert.assertTrue(response.asString().contains("women's clothing"));



    }
}
