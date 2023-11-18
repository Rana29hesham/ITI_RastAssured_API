package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC008_UpdateCart {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_UpdatCart_001 (){

        String productdata = "  {\n" +
                "                    \"userId\":3,\n" +
                "                    \"date\":\"2019-12-10\",\n" +
                "                    \"products\":[\n" +
                "                        {\n" +
                "                        \"productId\":1,\n" +
                "                        \"quantity\":3\n" +
                "                        \n" +
                "                        }]\n" +
                "                }\n";

        Response response =
                given()
                        .spec(baseClass.Request())
                        .contentType(ContentType.JSON)
                        .body(productdata)

                .when()
                        .put("carts/7")
                .then()
                        .assertThat().statusCode(200)
                        .log().body()
                        .log().status()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("$").isEmpty());


    }



}
