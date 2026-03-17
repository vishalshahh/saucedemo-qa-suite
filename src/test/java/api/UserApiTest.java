package api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(description = "GET user should return 200 and correct data")
    public void testGetUser() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("email", notNullValue());
    }

    @Test(description = "GET all posts should return 200 and list")
    public void testGetAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "POST create post should return 201")
    public void testCreatePost() {
        String body = """
            {
                "title": "QA Test Post",
                "body": "Created by Rest Assured",
                "userId": 1
            }
            """;

        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("QA Test Post"))
                .body("userId", equalTo(1));
    }

    @Test(description = "DELETE post should return 200")
    public void testDeletePost() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test(description = "GET non-existent user should return 404")
    public void testUserNotFound() {
        given()
                .when()
                .get("/users/9999")
                .then()
                .statusCode(404);
    }
}