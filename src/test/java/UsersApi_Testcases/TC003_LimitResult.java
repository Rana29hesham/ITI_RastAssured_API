package UsersApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC003_LimitResult {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_LimitResult_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("users?limit=5")
        .then()
                .assertThat().statusCode(200)
                .log().status();
    }
    @Test (priority = 2 ,description = "2-check that All users data arenot empty")
    public void TC_LimitResult_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users?limit=5")
        .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(!response.jsonPath().getList("$").isEmpty());
    }


    @Test (priority = 3 ,description = "3-Check Email Format Validation")
    public void TC_LimitResult_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users?limit=5")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath json = new JsonPath(response.asString());

        String emailRegex = "^(.+)@(.+)$";

        for(int i = 0 ; i<json.getList("$").size();i++){

            String email =json.getString("["+i+"].email");

            System.out.println(json.getString("["+i+"].email"));

            Assert.assertTrue(email.matches(emailRegex));

        }

    }

    @Test(priority = 4 ,description = "4-check that number of users is equal to 5")
    public void TC_LimitResult_004 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts?limit=5")
                .then()
                        .extract().response();

        Assert.assertTrue(response.jsonPath().getList("$").size()==5);
        System.out.println("Number of users :" + response.jsonPath().getList("$").size());


    }




}
