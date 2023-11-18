package CartsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;

public class TC005_GetCartsInDateRange  {
    BaseClass baseClass =new BaseClass();

    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_CartsInDateRange_001 (){
        given()
                .spec(baseClass.Request())
        .when()
                .get("carts?startdate=2019-12-10&enddate=2020-10-10")
        .then()
                .assertThat().statusCode(200)
                .log().status();

    }

    @Test (priority = 2 ,description = "3-Check that All carts have userID")
    public void TC_CartsInDateRange_002 (){
        Response response = given()
                .spec(baseClass.Request())
        .when()
                .get("carts?startdate=2019-12-10&enddate=2020-10-10")
        .then()
                .log().ifError()
                .extract().response();

        JsonPath path = new JsonPath(response.asString());

        for(int i=0 ; i<path.getList("$").size(); i++) {
            Assert.assertTrue(!path.getString("[" + i + "].userId").isEmpty());
            System.out.println(path.getString("[" + i + "].userId"));

        }

    }

    @Test (priority = 3 ,description = "4-Check that All products have quantity")
    public void TC_CartsInDateRange_003 (){
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts?startdate=2019-12-10&enddate=2020-10-10")
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

    @Test(priority = 4,description = "5-Check that date of all carts between (2019-12-10 - 2020-10-10)")
    public void TC_CartsInDateRange_005 () {
        Response response =
                given()
                        .spec(baseClass.Request())
                .when()
                        .get("carts?startdate=2019-12-10&enddate=2020-10-10")
                .then()
                        .log().status()
                        .extract().response();

        JsonPath json = new JsonPath(response.asString());

        for (int i = 0; i < json.getList("$").size(); i++) {

            String date = json.getString("[" + i + "].date");


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //parse string into date format
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter); //parse string into date format


            System.out.println(dateTime);

            LocalDateTime startDate = LocalDateTime.parse("2019-12-10T00:00:00");
            LocalDateTime endDate = LocalDateTime.parse("2020-10-10T00:00:00");

            Assert.assertTrue(dateTime.isAfter(startDate) && dateTime.isBefore(endDate));


        }




    }}
