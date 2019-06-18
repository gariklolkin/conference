package com.kyriba.conference.discount;

import com.kyriba.conference.discount.api.dto.DiscountExternalDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.domain.DiscountType;
import com.kyriba.conference.discount.service.DiscountService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.kyriba.conference.discount.domain.DiscountType.JUNIOR;
import static com.kyriba.conference.discount.domain.DiscountType.STUDENT;
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

    @MockBean
    private DiscountService discountService;

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
        Mockito.when(discountService.getAllDiscounts()).thenReturn(Arrays.asList(new DiscountExternalDto(JUNIOR, 30),
                new DiscountExternalDto(STUDENT, 50)));
        List<DiscountExternalDto> discounts = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("getDiscounts"))
                .when()
                .get("/api/v1/discounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().getList(".", DiscountExternalDto.class);
        assertEquals(2, discounts.size());
        assertEquals(DiscountType.JUNIOR, discounts.get(0).getType());
        assertEquals(STUDENT, discounts.get(1).getType());
    }

    @Test
    public void createDiscount() {
        Mockito.when(discountService.createDiscount(Mockito.any(DiscountExternalDto.class)))
                .thenReturn(new DiscountResponse(STUDENT));
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("createDiscount"))
                .body("{\n" +
                        "  \"type\": \"STUDENT\",\n" +
                        "  \"percentage\": 50\n" +
                        "}")
                .when()
                .post("/api/v1/discounts")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals(STUDENT.toString(), type);
    }

    @Test
    public void getDiscount() {
        Mockito.when(discountService.getDiscount(Mockito.anyString()))
                .thenReturn(new DiscountExternalDto(STUDENT, 30));
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("getDiscount"))
                .when()
                .get("/api/v1/discounts/student")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().get("type");
        assertEquals(STUDENT.toString(), type);
    }

    @Test
    public void updateDiscount() {
        given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("updateDiscount"))
                .body("{\n" +
                        "  \"percentage\": 50\n" +
                        "}")
                .when()
                .put("/api/v1/discounts/student")
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteDiscount() {
        given(this.spec)
                .when()
                .filter(document("deleteDiscount"))
                .delete("/api/v1/discounts/junior")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
