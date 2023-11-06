package com.rest.bookservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class BookserviceApplicationTests {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost/";
		RestAssured.port = Integer.valueOf(8081);
	}


	@Test
	void testGetBookByISBN() {
		given()
				.when()
				.get("/books/1234")
				.then()
				.statusCode(200)
				.body("isbn", equalTo("1234"))
				.contentType(ContentType.JSON);
	}

	@Test
	void testGetAllBooks() {
		given()
				.when()
				.get("/books")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON);
	}

	@Test
	void testAddBook() {
		Book book2 = new Book("5678","naveed","Potter",13.4);

		given()
				.contentType(ContentType.JSON)
				.body(book2)
				.when()
				.post("/books")
				.then()
				.statusCode(201);
	}

	@Test
	void testUpdateBook() {
		Book book2 = new Book("1234","naveed","New Title",13.4);

		given()
				.contentType(ContentType.JSON)
				.body(book2)
				.when()
				.put("/books")
				.then()
				.statusCode(200);
	}

	@Test
	void testUpdateBookWhenBookDoesntExist() {
		Book book2 = new Book("57382","naveed","New Title",13.4);

		given()
				.contentType(ContentType.JSON)
				.body(book2)
				.when()
				.put("/books")
				.then()
				.statusCode(404);
	}

	@Test
	void testDeleteBook() {
		given()
				.when()
				.delete("/books/5678")
				.then()
				.statusCode(200);
	}

}
