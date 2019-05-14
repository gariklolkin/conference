package com.kyriba.payment;

import com.kyriba.payment.domain.PaymentMethodType;
import com.kyriba.payment.domain.dto.PaymentMethodDto;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PaymentMethodControllerTest {
    @Test
    public void getPaymentMethods() {
        List<PaymentMethodDto> methods = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
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
        String type = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
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
        String type = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
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
        String type = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
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
        String type = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .body("{\n" +
                        "  \"type\": \"CREDIT_CARD\",\n" +
                        "  \"url\": \"https://webpay.by/en/\"\n" +
                        "}\n")
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
        given()
                .when()
                .delete("/v1/paymentMethod/wire_transfer")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
