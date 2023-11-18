package ProductsApi_Testcases;

import BaseClass.BaseClass;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC009_DeleteProduct  {
    BaseClass baseClass =new BaseClass();
    @Test(priority = 1 ,description = "1-Check Status Code")
    public void TC_DeletePeroduct_001 (){

              given()
                      .spec(baseClass.Request())
                   .contentType(ContentType.JSON)


              .when()
                        .delete("products/6")
              .then()
                        .assertThat().statusCode(200)
                        .log().status();



    }

}
