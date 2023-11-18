package ProductsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC002_GetSingleProduct  {

    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SingleProduct_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("products/1")
        .then()
                .assertThat().statusCode(200)
                .log().status();


    }

    @Test(priority = 2 ,description = "2-check that All product data (title, price, images, description) arenot empty")
    public void TC_SingleProduct_002 (){
        Response response =given()
                .spec(baseClass.Request())
        .when()
                .get("products/1")
        .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.getBody()!=null);


    }

    @Test(priority = 3 ,description = "3-check Response body includes (title, price, image, description , rating)")
    public void TC_SingleProduct_003(){
        Response response =given()
                .spec(baseClass.Request())
        .when()
                .get("products/1")
        .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(response.asString().contains("title"));
        Assert.assertTrue(response.asString().contains("price"));
        Assert.assertTrue(response.asString().contains("image"));
        Assert.assertTrue(response.asString().contains("description"));
        Assert.assertTrue(response.asString().contains("rating"));


    }




}
