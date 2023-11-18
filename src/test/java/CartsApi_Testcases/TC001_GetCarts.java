package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC001_GetCarts  {

    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_Carts_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("carts")
        .then()
                .assertThat().statusCode(200)
        .log().status();

    }

    @Test (priority = 2 ,description = "2-Check that All carts have userID")
    public void TC_Carts_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts")
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
    public void TC_Carts_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {

            Assert.assertTrue(!path.getString("[" + i + "].date").isEmpty());
            System.out.println(path.getString("[" + i + "].date"));


        }

    }




}
