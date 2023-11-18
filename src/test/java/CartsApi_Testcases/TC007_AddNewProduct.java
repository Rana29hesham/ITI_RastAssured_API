package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC007_AddNewProduct  {

    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_AddProduct_001 (){

        String productdata = "  {\n" +
                "                    \"userId\":5,\n" +
                "                    \"date\":\"2020-02-03\",\n" +
                "                    \"products\":[\n" +
                "                        {\n" +
                "                            \"productId\":5,\n" +
                "                             \"quantity\":1\n" +
                "                            },\n" +
                "                            \n" +
                "                            {\n" +
                "                                \"productId\":1,\n" +
                "                                 \"quantity\":5\n" +
                "                                }\n" +
                "                        ]\n" +
                "                }";

        Response response =
                given()
                        .spec(baseClass.Request())
                        .contentType(ContentType.JSON)
                        .body(productdata)

                .when()
                        .post("carts")
                .then()
                        .assertThat().statusCode(200)
                        .log().body()
                        .log().status()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("$").isEmpty());


    }






}
