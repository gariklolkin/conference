package com.kyriba.payment;

import com.kyriba.payment.domain.PaymentStatus;
import com.kyriba.payment.domain.dto.PaymentDto;
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
import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

/**
 * @author Igor Lizura
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PaymentControllerTest {

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
    public void getPayments() {
        List<PaymentDto> payments = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payment/getPayments"))
                .when()
                .get("/v1/payment")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().getList(".", PaymentDto.class);
        assertEquals(1, payments.size());
        assertEquals(PaymentStatus.PENDING, payments.get(0).getStatus());
    }

    @Test
    public void createPayment() {
        String status = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payment/createPayment"))
                .body(getRequestJson())
                .when()
                .post("/v1/payment")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("status");
        assertEquals("PENDING", status);
    }

    @Test
    public void getPayment() {
        String status = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payment/getPayment"))
                .when()
                .get("/v1/payment/3")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().get("status");
        assertEquals("PENDING", status);
    }

    @Test
    public void updatePayment() {
        int id = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payment/updatePayment"))
                .body(getRequestJson())
                .when()
                .put("/v1/payment/5")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("id");
        assertEquals(3, id);
    }

    @Test
    public void patchPayment() {
        int id = given(this.spec)
                .param("status", PaymentStatus.COMPLETED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payment/patchPayment"))
                .when()
                .patch("/v1/payment/5")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("id");
        assertEquals(5, id);
    }

    @Test
    public void deletePaymentMethod() {
        given(this.spec)
                .filter(document("payment/deletePaymentMethod"))
                .when()
                .delete("/v1/payment/5")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    private String getRequestJson() {
        //language=JSON
        return  "{\n" +
                "  \"userId\":123,\n" +
                "  \"paymentMethodId\":3,\n" +
                "  \"paymentDate\":\"12::05::2019 20:30\",\n" +
                "  \"price\":{\n" +
                "    \"value\":250.0,\n" +
                "    \"currency\":\"EUR\"\n" +
                "  }\n" +
                "}";
    }
}
