import com.jayway.restassured.RestAssured;
import dto.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuedTests {

    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginTestPositive(){
        AuthRequestDto body = AuthRequestDto.builder()
                .email("asd@asd.com")
                .password("aA123456$")
                .build();

        AuthResponseDto responseDto = given().contentType("application/json")
                .body(body)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println(responseDto.getToken());
    }

    @Test
    public void LoginTestNegativeInvalidPassword(){
        AuthRequestDto body = AuthRequestDto.builder()
                .email("asd@asd.com")
                .password("aA123456")
                .build();
        ErrorDto errorDto = given().contentType("application/json")
                .body(body)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage());
    }

    @Test
    public void getAllContactsTest(){
        GetAllContactsDto contactsDto = given()
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1haWwzQC5jb20ifQ.8hMaLWf2Omhx43YmOwROYSU3b_pF4aSxtwcKirAOAbM")
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDto.class);
                for(ContactDto contact : contactsDto.getContacts()){
                    System.out.println(contact.getId() + " " + contact.getEmail());
                    System.out.println("=========================================");
                }
    }

    @Test
    public void addNewContactTest(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1haWwzQC5jb20ifQ.8hMaLWf2Omhx43YmOwROYSU3b_pF4aSxtwcKirAOAbM";
        ContactDto contact = ContactDto.builder()
                .name("asdasd")
                .lastName("asdasd")
                .email("asdasd@asdasd.asd" + (System.currentTimeMillis()/1000) % 3600)
                .phone("1234567" + (System.currentTimeMillis()/1000) % 3600)
                .address("sdfaasdf")
                .description("asdasd")
                .build();
        int id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contact)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }

    @Test
    public void deleteContactTest(){ //14023
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1haWwzQC5jb20ifQ.8hMaLWf2Omhx43YmOwROYSU3b_pF4aSxtwcKirAOAbM";
        String status = given().header("Authorization", token)
                .when()
                .delete("contact/14023")
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
    }
}
