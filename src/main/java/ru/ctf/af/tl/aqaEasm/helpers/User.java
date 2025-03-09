package ru.ctf.af.tl.aqaEasm.helpers;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String fullName;
    private String email;
    private String phone;
    private String password;

    public User() {
        Faker faker = new Faker();
        this.fullName = faker.name().fullName().replaceAll("[^a-zA-Zа-яА-Я ]", "");
        this.email = faker.internet().safeEmailAddress();
        this.phone = "7965" + faker.number().digits(7);
        this.password = faker.internet().password(8, 12);
    }
}