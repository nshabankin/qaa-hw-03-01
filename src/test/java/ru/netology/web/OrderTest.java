package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderTest {

    // Объекты тестовых элементов с css-селекторами
    SelenideElement name = $("[data-test-id=name]");
    SelenideElement phone = $("[data-test-id=phone]");
    SelenideElement agreement = $("[data-test-id=agreement]");
    SelenideElement submit = $(".button.button_view_extra.button_size_m.button_theme_alfa-on-white");
    SelenideElement successMessage = $("[data-test-id=order-success]");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitIfAllCorrect() {

        // Ввод минимального валидного значения имени и фамилии, и его проверка
        name.$(".input__control").setValue("а а");
        name.$(".input__control").shouldHave(value("а а"));

        // Ввод валидного значения номера телефона и его проверка
        phone.$(" .input__control").setValue("+00000000000");
        phone.$(" .input__control").shouldHave(value("+00000000000"));

        // Клик на чек-бокс с "соглашением" и проверка статуса чек-бокса
        agreement.$(".checkbox__box").click();
        agreement.$(".checkbox__control").shouldBe(selected);

        // Клик на кнопку "Продолжить"
        submit.click();

        // Проверка сообщения после отправки формы
        successMessage.shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotifyIfIncorrectName() {

        // Ввод некорректного значения имени и фамилии
        name.$(".input__control").setValue("f f");

        // Ввод валидного значения номера телефона
        phone.$(".input__control").setValue("+00000000000");

        // Клик на чек-бокс с "соглашением"
        agreement.$(".checkbox__box").click();

        // Клик на кнопку "Продолжить"
        submit.click();

        // Проверка сообщения после отправки формы в поле имени
        name.$(".input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotifyIfIncorrectPhone() {

        // Ввод корректного значения имени и фамилии
        name.$(".input__control").setValue("а а");

        // Ввод некорректного значения номера телефона
        phone.$(".input__control").setValue("00000000000");

        // Клик на чек-бокс с "соглашением"
        agreement.$(".checkbox__box").click();

        // Клик на кнопку "Продолжить"
        submit.click();

        // Проверка сообщения после отправки формы в поле номера телефона
        phone.$(".input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotifyIfNoAgreement() {

        // Ввод корректного значения имени и фамилии
        name.$(".input__control").setValue("а а");

        // Ввод корректного значения номера телефона
        phone.$(".input__control").setValue("+00000000000");

        // Клик на кнопку "Продолжить" без клика на чек-бокс с "соглашением"
        submit.click();

        // Проверка добавления нового класса в элемент с "соглашением"
        agreement.shouldHave(cssClass("input_invalid"));
    }

}
