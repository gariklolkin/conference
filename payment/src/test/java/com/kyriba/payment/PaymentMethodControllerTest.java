package com.kyriba.payment;

import com.kyriba.payment.domain.PaymentMethodType;
import com.kyriba.payment.domain.dto.PaymentMethodDto;
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

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PaymentMethodControllerTest {

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
    public void getPaymentMethods() {
        List<PaymentMethodDto> methods = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("paymentMethod/getPaymentMethods"))
                .when()
                .get("/v1/paymentMethod")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().getList(".", PaymentMethodDto.class);
        assertEquals(2, methods.size());
        assertEquals(PaymentMethodType.WIRE_TRANSFER, methods.get(0).getType());
        assertEquals(PaymentMethodType.CREDIT_CARD, methods.get(1).getType());
    }

    @Test
    public void createPaymentMethod() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("paymentMethod/createPaymentMethod"))
                .body("{\n" +
                        "  \"type\": \"CREDIT_CARD\",\n" +
                        "  \"url\": \"https://webpay.by/en/\"\n" +
                        "}\n")
                .when()
                .post("/v1/paymentMethod")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals("CREDIT_CARD", type);
    }

    @Test
    public void getPaymentMethod() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("paymentMethod/getPaymentMethod"))
                .when()
                .get("/v1/paymentMethod/wire_transfer")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().get("type");
        assertEquals("WIRE_TRANSFER", type);
    }

    @Test
    public void updatePaymentMethod() {
        String type = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("paymentMethod/updatePaymentMethod"))
                .body("{\n" +
                        "  \"type\": \"CREDIT_CARD\",\n" +
                        "  \"url\": \"https://webpay.by/en/\"\n" +
                        "}\n")
                .when()
                .put("/v1/paymentMethod/credit_card")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals("CREDIT_CARD", type);
    }

    @Test
    public void patchPaymentMethod() {
        String type = given(this.spec)
                .param("url", "https://webpay.by/en")
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("paymentMethod/patchPaymentMethod"))
                .when()
                .patch("/v1/paymentMethod/wire_transfer")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("type");
        assertEquals("WIRE_TRANSFER", type);
    }

    @Test
    public void deletePaymentMethod() {
        given(this.spec)
                .filter(document("paymentMethod/deletePaymentMethod"))
                .when()
                .delete("/v1/paymentMethod/wire_transfer")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
