package UsersApi_Testcases;

import BaseClass.BaseClass;
import DataClasses.User_data;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC006_UpdateUser {
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_UpdateUser_001 (){
        User_data userdata = new User_data(
                "ohn@gmail.com",
                "johnd",
                "m38rmF$",
                "John",
                "Doe",
                "kilcoole",
                "7835 new road",
                3,
                "12926-3874",
                "-37.3159",
                "81.1496",
                "1-570-236-7033");

        Response response =
                given()
                        .spec(baseClass.Request())
                        .contentType(ContentType.JSON)
                        .body(userdata)

                .when()
                        .put("users/7")
                .then()
                        .assertThat().statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("$").isEmpty());
        Assert.assertEquals(json.getString("street"),"7835 new road");
        Assert.assertEquals(json.getString("lastname"),"Doe");




    }
}
