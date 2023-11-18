package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC002_GetSingleCart  {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SingleCart_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("carts/5")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test (priority = 2 ,description = "2-Check that cart has userID")
    public void TC_SingleCart_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts/5")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("userId").isEmpty());

    }


    @Test (priority = 3 ,description = "3-Check that cart has date")
    public void TC_SingleCart_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts/5")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("date").isEmpty());

    }

    @Test (priority = 4 ,description = "4-Check that All products have quantity")
    public void TC_SingleCart_004 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts/5")
        .then()
                .log().body()
                .extract().response();

        JsonPath json = new JsonPath(response.asString());

        for(int i =0 ; i< json.getList("products").size() ;i++){
            String quantity = json.getString("products[" + i + "].quantity");
            int quantityInt = Integer.parseInt(json.getString("products[" + i + "].quantity"));
            Assert.assertTrue(quantityInt>0);
            System.out.println("Quantity of Product "+(i+1)+":"+quantity);

        }

    }







}
