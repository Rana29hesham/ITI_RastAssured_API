package ProductsApi_Testcases;


import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TC001_GetProducts {

    BaseClass baseClass =new BaseClass();

    @Test (priority = 1 ,description = "1-Check Status Code")
    public void TC_Products_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("products")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test (priority = 2 ,description = "2-check that All products data – title, price, image, description arenot empty")
    public void TC_Products_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("products")
        .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(!response.jsonPath().getList("$").isEmpty());
    }

    @Test (priority = 3 ,description = "3-check Response body includes (title, price, image, description, rating")
    public void TC_Products_003 (){
      Response response = given()
              .spec(baseClass.Request())
       .when()
                .get("products")
       .then()
               .log().ifError()
               .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {

            Assert.assertTrue(path.getString("[" + i + "]").contains("title"));
            Assert.assertTrue(path.getString("[" + i + "]").contains("price"));
            Assert.assertTrue(path.getString("[" + i + "]").contains("image"));
            Assert.assertTrue(path.getString("[" + i + "]").contains("description"));


        }


    }

    @Test (priority = 4 ,description = "4-check that the total number of products is equal to 20")
    public void TC_Products_004 (){
        Response response =given()
                .spec(baseClass.Request())
        .when()
                .get("products")
        .then()
                .extract().response();

        JsonPath path =new JsonPath(response.asString());

        Assert.assertEquals(path.getList("$").size(), 20);
        System.out.println(response.jsonPath().getList("$").size());
    }




}
