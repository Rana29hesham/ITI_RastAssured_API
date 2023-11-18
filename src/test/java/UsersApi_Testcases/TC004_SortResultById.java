package UsersApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TC004_SortResultById {
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SortResult_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("users?sort=desc")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }


    @Test(priority = 2 ,description = "2-check that All users data arenot empty")
    public void TC_SortResult_002 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("users?sort=desc")
                .then()
                        .log().body()
                        .extract().response();
        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(json.getList("$")!=null);


    }

    @Test(priority = 3 ,description = "3-check that users sorted by id desc")
    public void TC_SortResult_003 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("users?sort=desc")
                        .then()
                        .extract().response();


        JsonPath path = new JsonPath(response.asString());    // to convert body to json
        List<Integer> actualResult = path.getList("id");
        System.out.println("ActualResult : "+actualResult);

        List<Integer> expectedResult = new ArrayList<>(actualResult); // Create a new list with the same elements as actualResult
        Collections.sort(expectedResult, Collections.reverseOrder());// to order id desc
        System.out.println("---------------------------------------------------------");
        System.out.println("ActualResult :"+expectedResult);

        Assert.assertEquals(expectedResult, actualResult);


    }

    @Test (priority = 4,description = "4-Check Email Format Validation")
    public void TC_SortResult_004 (){
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


}
