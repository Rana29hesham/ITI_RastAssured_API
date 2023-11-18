package ProductsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC006_GetProductsInSpecificCategory {

    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SpecificCategory_001 () {
        given()
                .spec(baseClass.Request())
        .when()
                .get("products/categories")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test(priority = 2 , description = "2-Check that all products are in jewelery category")
    public void TC_SpecificCategory_002 () {
        Response response =
        given()
                .spec(baseClass.Request())
        .when()
                .get("products/category/jewelery")
        .then()
                .log().body()
                .extract().response();


        JsonPath path = new JsonPath(response.asString());
        for(int i=0 ; i<path.getList("$").size() ; i++) {

        Assert.assertEquals(path.getString("["+i+"].category"),"jewelery");

        System.out.println("Category of Product "+(i+1)+":"+ path.getString("["+i+"].category"));

        }




    }








}
