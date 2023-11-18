package ProductsApi_Testcases;

import BaseClass.BaseClass;
import DataClasses.Product_data;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class TC008_UpdateProduct  {
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_UpdatePeroduct_001 (){
        Product_data productdata = new Product_data(
                "test product",
                13.5f,
                "lorem ipsum set",
                "https://i.pravatar.cc",
                "electronic");

        Response response =
                given()
                        .spec(baseClass.Request())
                        .contentType(ContentType.JSON)
                        .body(productdata)

                .when()
                        .put("products/7")
                .then()
                        .assertThat().statusCode(200)
                        .log().status()
                        .log().body()
                        .extract().response();

       JsonPath json = new JsonPath(response.asString());

        Assert.assertTrue(!json.getString("$").isEmpty());


    }

}
