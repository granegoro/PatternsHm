import com.github.javafaker.Faker;
import lombok.Value;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {

    }

    static String clearAll = Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.DELETE;

    static Faker faker = new Faker(new Locale("ru"));


    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        return date;

    }

    public static String generateCity(String locale) {

        var cities = new String[]{
                "Москва", "Майкоп", "Уфа", "Симферополь", "Сыктывкар", "Элиста", "Махачкала", "Хабаровск"
        };
        return cities[new Random().nextInt(cities.length)];

    }

    public static String generateName(String locale) {
        String name = faker.address().firstName();
        return name;

    }

    public static String generatePhone(String locale) {
        String phone = "+7" + faker.phoneNumber().cellPhone();
        return phone;

    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            return new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale)
            );

        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}
