package ru.ctf.af.tl.aqaEasm.helpers;

import org.testng.annotations.DataProvider;

public class TestsDataProviders {
    @DataProvider(name = "apiInvalidPhoneAndMessages")
    public Object[][] createInvalidPhoneUser() {
        return new Object[][]{
                {"         ", false, ErrorMessages.PHONE_ONLY_NUMBERS_OR_PLUS},
                {"+йцукенгш", false, ErrorMessages.PHONE_ONLY_NUMBERS_OR_PLUS},
                {"+!%:,.;()", false, ErrorMessages.PHONE_ONLY_NUMBERS_OR_PLUS},
                /* Следующий кейс - с одной стороны ошибка логична. Но с другой стороны может быть сообщения что поле не может быть пустым */
                {"", false, ErrorMessages.PHONE_ONLY_NUMBERS_OR_PLUS},
                /* Следующий кейс - должна быть другая ошибка (например, Неверный формат телефона), тк введен символ +, что соответвует возможному значению поля исходя из текста ошибки */
                {"+", false, ErrorMessages.PHONE_ONLY_NUMBERS_OR_PLUS},
                /* Следующий кейс - другой формат сообщения об ошибке (на латинице) + логичнее была бы ошибка о том? что телефон не может быть более 10 чисел */
                {"8911916295489119162954891191629548911916295489119162954", false, ErrorMessages.PHONE_TOO_LONG},
                /* Следующие 2 кейса - в ответе не приходит поле message с текстом "Введите номер телефона в таком формате: 79991112233 или 89991112233. Он должен начинаться с 7 или 8 и не содержать других символов кроме цифр". Поэтому оставлены для срабатывания */
                {"19889881612", false, ErrorMessages.PHONE_INVALID_FORMAT},
                {"6", false, ErrorMessages.PHONE_INVALID_FORMAT},
        };
    }

    @DataProvider(name = "apiInvalidFullNamesAndMessages")
    public Object[][] createInvalidNamedUser() {
        return new Object[][]{
                {"", false, ErrorMessages.FULL_NAME_EMPTY},
                {"\"Andy12@++-\"\n", false, ErrorMessages.FULL_NAME_TEXT_ONLY}, /* тут ошибка отличается от ошибки отображаемой в UI */
                {"s", false, ErrorMessages.FULL_NAME_TOO_SHORT},
                {"QWERTYUIOPasdfghjklZXCVBNMzxcvbnm", false, ErrorMessages.FULL_NAME_TOO_LONG},
                /* Следующие 2 кейса - аналогичные проверки есть в SignUpGuiTests, но тут отличается сообщение об ошибке */
                {"Ивён Ивёнович Ивёнов", false, ErrorMessages.FULL_NAME_TEXT_ONLY},
                {"Èmanuel Górdan", false, ErrorMessages.FULL_NAME_TEXT_ONLY}
        };
    }

    @DataProvider(name = "uiInvalidFullNamesError")
    public Object[][] createTestData() {
        return new Object[][]{
                {"1234567890~!@#$%^*()_+", ErrorMessages.FULL_NAME_INVALID_CHARACTERS},
                {"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", ErrorMessages.FULL_NAME_TOO_LONG},
                {"Ы ", ErrorMessages.FULL_NAME_TOO_SHORT_UI},
                /* Следующие 2 должны проходить тк соответствуют условию - символы киррилицы или латиницы. Но не валидируются из-за конфигурации signUpForm.js */
                {"Иван Ёвовёч Ёвов", ErrorMessages.FULL_NAME_INVALID_CHARACTERS},
                {"Èmanuel Górdan", ErrorMessages.FULL_NAME_INVALID_CHARACTERS}};
    }
}
