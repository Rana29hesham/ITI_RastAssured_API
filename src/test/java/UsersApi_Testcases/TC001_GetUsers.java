package UsersApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC001_GetUsers{
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_Users_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("users")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test (priority = 2 ,description = "2-check that All users data arenot empty")
    public void TC_Users_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users")
        .then()
                .log().body()
                .extract().response();

        Assert.assertTrue(!response.jsonPath().getList("$").isEmpty());
    }

    @Test (priority = 3 ,description = "3-Check Email Format Validation")
    public void TC_Users_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users")
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





}
