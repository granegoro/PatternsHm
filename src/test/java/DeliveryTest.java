import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);


        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=date] input").setValue(DataGenerator.clearAll);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[class=checkbox__text]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting)), Duration.ofSeconds(15)).shouldBe(visible);

        $("button.notification__closer .icon-button__content").click();

        $("[data-test-id=date] input").setValue(DataGenerator.clearAll);
        $("[data-test-id=date] input").setValue(DataGenerator.generateDate(daysToAddForSecondMeeting));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));



        $("[data-test-id=replan-notification] .notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15)).shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForSecondMeeting)), Duration.ofSeconds(15)).shouldBe(visible);

    }
}
