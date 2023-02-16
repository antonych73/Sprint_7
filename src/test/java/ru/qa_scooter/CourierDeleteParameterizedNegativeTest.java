package ru.qa_scooter;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierDeleteParameterizedNegativeTest {

    private CourierMethods courierMethods;
    private final String courierId;
    private final int status;
    private final String message;

    public CourierDeleteParameterizedNegativeTest(String courierId,
                                                  int status,
                                                  String message) {
        this.courierId = courierId;
        this.status = status;
        this.message = message;
    }

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {EMPTY, 404, "Not Found."},
                {null, 500, "invalid input syntax for type integer: \"null\""},
                {"0", 404, "Курьера с таким id нет."}
        };
    }


    @Test
    @DisplayName("Check deleting of courier: courier was not deleted due to wrong id")
    @Description("It is checked that it is impossible to delete courier without id or with wrong id")
    public void testGetResponse() {

        ValidatableResponse response = courierMethods.delete(courierId); // Удаление курьера

        response.assertThat().statusCode(status).and()
                .body("code", equalTo(status), "message", equalTo(message)); // Получение ответа + Проверка
    }
}