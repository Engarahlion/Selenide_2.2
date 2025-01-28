import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    private String dateGenerator(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCheckReservationForm() {
        String planningDate = dateGenerator(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Симферополь");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79781231231");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $$("[data-test-id='notification']").findBy(Condition.text("Встреча успешно забронирована на " + planningDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}