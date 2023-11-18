package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC006_GetUserCart  {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_GetUserCart_001 (){
        given()
                .spec(baseClass.Request())
         .when()
                .get("carts/user/1")
         .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test (priority = 2 ,description = "2-Check that All carts have userID")
    public void TC_GetUserCart_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts/user/1")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {
            Assert.assertTrue(!path.getString("[" + i + "].userId").isEmpty());
            System.out.println(path.getString("[" + i + "].userId"));

        }

    }

    @Test (priority = 3 ,description = "3-Check that All carts have date")
    public void TC_GetUserCart_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts/user/1")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {

            Assert.assertTrue(!path.getString("[" + i + "].date").isEmpty());
            System.out.println(path.getString("[" + i + "].date"));

        }

    }


    @Test (priority = 5 ,description = "4-Check that All products have quantity")
    public void TC_GetUserCart_004 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts/user/1")
                .then()
                        .log().body()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        for (int i =0 ; i< json.getList("$").size() ;i++){

            for(int j =0 ; j< json.getList("[" + i + "].products").size();j++) {

                String quantity = json.getString("[" + i + "].products[" + j + "].quantity");
                int quantityInt = Integer.parseInt(json.getString("[" + i + "].products[" + j + "].quantity"));
                Assert.assertTrue(quantityInt>0);
                System.out.println("---------------------------------------------");
                System.out.println("Quantity of Product "+(j+1)+":"+quantity);

            }

        }

    }





}
