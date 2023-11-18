package UsersApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC002_GetSingleUser {

    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SingleUser_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("users/1")
        .then()
                .assertThat().statusCode(200)
                .log().status();


    }
    @Test (priority = 2 ,description = "2-check that All user data isnot empty")
    public void TC_SingleUser_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users/1")
        .then()
                //.log().body()
                .extract().response();
        JsonPath json = new JsonPath(response.asString());
        Assert.assertTrue(!json.getString("$").isEmpty());

        //System.out.println(json.getString("email"));

    }

    @Test (priority = 3 ,description = "3-Check Email Format Validation")
    public void TC_SingleUser_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("users/1")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath json = new JsonPath(response.asString());

        String emailRegex = "^(.+)@(.+)$";
        String email =json.getString("email");
        System.out.println(json.getString("email"));
        Assert.assertTrue(email.matches(emailRegex));



    }

}
