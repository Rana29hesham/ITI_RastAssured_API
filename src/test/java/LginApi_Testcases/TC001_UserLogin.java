package LginApi_Testcases;

import BaseClass.BaseClass;
import DataClasses.User_data;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;


public class TC001_UserLogin {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code=200 | and Response body has (token) key")
    public void TC_UserLogin_001 (){

        User_data userdata = new User_data(
                "mor_2314",
                 "83r5^_"
                );

              given()
                        .spec(baseClass.Request())
                        .contentType(ContentType.JSON)
                        .body(userdata)
                .when()
                        .post("auth/login")
                .then()
                        .assertThat().statusCode(200)
                      .body("$",hasKey("token"))
                        .log().body()
                        .log().status();






    }
}
