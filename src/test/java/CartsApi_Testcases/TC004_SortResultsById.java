package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TC004_SortResultsById {

    BaseClass baseClass=new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_SortResults_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("carts?sort=desc")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test(priority = 2 ,description = "4-check that carts sorted by id desc")
    public void TC_SortResult_002 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts?sort=desc")
                .then()
                        .extract().response();


        JsonPath path = new JsonPath(response.asString());    // to convert body to json
        List<Integer> actualResult = path.getList("id");
        System.out.println("ActualResult : "+actualResult);

        List<Integer> expectedResult = new ArrayList<>(actualResult); // Create a new list with the same elements as actualResult
        Collections.sort(expectedResult, Collections.reverseOrder());  // to order id desc
        System.out.println("---------------------------------------------------------");
        System.out.println("ActualResult :"+expectedResult);

        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test (priority = 3 ,description = "3-Check that All carts have userID")
    public void TC_SortResult_003 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts?sort=desc")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {
            Assert.assertTrue(!path.getString("[" + i + "].userId").isEmpty());
            System.out.println(path.getString("[" + i + "].userId"));

        }

    }

    @Test (priority = 4 ,description = "4-Check that All carts have date")
    public void TC_SortResult_004 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts?sort=desc")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {

            Assert.assertTrue(!path.getString("[" + i + "].date").isEmpty());
            System.out.println(path.getString("[" + i + "].date"));

        }

    }

    @Test (priority = 5 ,description = "5-Check that All products have quantity")
    public void TC_SortResult_005 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts?limit=5")
                .then()
                        .log().body()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        for (int i =0 ; i< json.getList("$").size() ;i++){

            for(int j =0 ; j< json.getList("[" + i + "].products").size();j++) {

                String quantity = json.getString("[" + i + "].products[" + j + "].quantity");
                int quantityInt = Integer.parseInt(json.getString("[" + i + "].products[" + j + "].quantity"));
                Assert.assertTrue(quantityInt>0);
                System.out.println("---------------------------------------------");
                System.out.println("Quantity of Product "+(j+1)+":"+quantity);

            }

        }

    }



}
