package com.kyriba.conference.payment;

import com.kyriba.conference.payment.api.dto.PaymentDto;
import com.kyriba.conference.payment.api.dto.Receipt;
import com.kyriba.conference.payment.api.dto.TicketDto;
import com.kyriba.conference.payment.domain.dto.Amount;
import com.kyriba.conference.payment.service.PaymentService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.kyriba.conference.payment.domain.PaymentMethodType.CREDIT_CARD;
import static com.kyriba.conference.payment.domain.PaymentStatus.PENDING;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

/**
 * @author Igor Lizura
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private PaymentService paymentService;

    private RequestSpecification spec;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(
                documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void getPayments() {
        Mockito.doReturn(Collections.singletonList(
                new PaymentDto(123,
                        CREDIT_CARD,
                        LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                        new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                        PENDING))).when(paymentService).findAll();

        List<PaymentDto> payments = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payments/getPayments"))
                .when()
                .get("/api/v1/payments")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().getList(".", PaymentDto.class);
        assertEquals(1, payments.size());
        assertEquals(PENDING, payments.get(0).getStatus());
    }

    @Test
    public void createPayment() {
        Mockito.doReturn(new Receipt(
                new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                PENDING)).when(paymentService).createPayment(Mockito.any(TicketDto.class));

        String status = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payments/createPayment"))
                .body("{\n" +
                        "  \"userId\":123,\n" +
                        "  \"paymentMethodId\":3,\n" +
                        "  \"paymentDate\":\"12::05::2019 20:30\",\n" +
                        "  \"price\":{\n" +
                        "    \"value\":250.0,\n" +
                        "    \"currency\":\"EUR\"\n" +
                        "  }\n" +
                        "}")
                .when()
                .post("/api/v1/payments")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().get("status");
        assertEquals("PENDING", status);
    }

    @Test
    public void getPayment() {
        Mockito.doReturn(new PaymentDto(123,
                CREDIT_CARD,
                LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                PENDING)).when(paymentService).getPayment(Mockito.anyLong());

        String status = given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payments/getPayment"))
                .when()
                .get("/api/v1/payments/3")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath().get("status");
        assertEquals("PENDING", status);
    }

    @Test
    public void updatePayment() {
        given(this.spec)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .filter(document("payments/updatePayment"))
                .body("{\n" +
                        "  \"status\":\"COMPLETED\"\n" +
                        "}")
                .when()
                .put("/api/v1/payments/5")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deletePaymentMethod() {
        given(this.spec)
                .filter(document("payments/deletePayment"))
                .when()
                .delete("/api/v1/payments/5")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
