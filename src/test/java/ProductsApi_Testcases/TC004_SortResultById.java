package ProductsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TC004_SortResultById  {
    BaseClass baseClass = new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SortResult_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("products?sort=desc")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test(priority = 2 ,description = "2-check that All product data (title, price, images, description) arenot empty")
    public void TC_SortResult_002 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("products?sort=desc")
                .then()
                        .log().body()
                        .extract().response();

        Assert.assertTrue(response.jsonPath().getList("$")!=null);


    }
    @Test (priority = 3,description = "3-check Response body includes (title, price, image, description, rating")
    public void TC_SortResult_003 (){
        Response response =
        given()
                .spec(baseClass.Request())
        .when()
               .get("products?limit=5")
         .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {

            Assert.assertTrue(path.getString("["+i+"]").contains("title"));
            Assert.assertTrue(path.getString("["+i+"]").contains("price"));
            Assert.assertTrue(path.getString("["+i+"]").contains("image"));
            Assert.assertTrue(path.getString("["+i+"]").contains("description"));

            //System.out.println(path.getString("["+i+"].title"));

        }

    }


    @Test(priority = 4 ,description = "4-check that products sorted by id desc")
    public void TC_SortResult_004 (){
        Response response =
        given()
                .spec(baseClass.Request())
         .when()
               .get("products?sort=desc")
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


}
