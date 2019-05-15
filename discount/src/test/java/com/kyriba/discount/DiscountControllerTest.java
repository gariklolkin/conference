package com.kyriba.discount;

import com.kyriba.discount.domain.dto.DiscountDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.kyriba.discount.domain.DiscountType.JUNIOR;
import static com.kyriba.discount.domain.DiscountType.STUDENT;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

/**
 * @author Igor Lizura
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DiscountControllerTest {

    private RequestSpecification spec;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void setUp() {
        this.spec = new RequestSpecBuilder().addFilter(
                documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void getDiscounts() {
        List<DiscountDto> discounts = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("getDiscounts"))
                .when()
                .get("/v1/discount")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().getList(".", DiscountDto.class);
        assertEquals(2, discounts.size());
        assertEquals(JUNIOR, discounts.get(0).getType());
        assertEquals(STUDENT, discounts.get(1).getType());
    }

    @Test
    public void createDiscount() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("createDiscount"))
                .body("{\n" +
                        "  \"type\": \"STUDENT\",\n" +
                        "  \"percentage\": 50\n" +
                        "}")
                .when()
                .post("/v1/discount")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals(STUDENT.toString(), type);
    }

    @Test
    public void getDiscount() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("getDiscount"))
                .when()
                .get("/v1/discount/student")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().get("type");
        assertEquals(STUDENT.toString(), type);
    }

    @Test
    public void updateDiscount() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("updateDiscount"))
                .body("{\n" +
                        "  \"type\": \"STUDENT\",\n" +
                        "  \"percentage\": 50\n" +
                        "}")
                .when()
                .put("/v1/discount/student")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals(STUDENT.toString(), type);
    }

    @Test
    public void deleteDiscount() {
        given(this.spec)
                .when()
                .filter(document("deleteDiscount"))
                .delete("/v1/discount/junior")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
