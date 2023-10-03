package kg.BIZ.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kg.BIZ.validation.NameValid;
import kg.BIZ.validation.PasswordValid;
import kg.BIZ.validation.PhoneNumberValid;
import lombok.Builder;

@Builder
public record VolunteerRegisterRequest (
        @NameValid(message = "Имя должно содержать от 2 до 33 символов.")
        @NotBlank(message = "Необходимо указать имя.")
        String firstName,
        @NameValid(message = "Фамилия должна содержать от 2 до 33 символов.")
        @NotBlank(message = "Необходимо указать фамилию.")
        String lastName,
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String phoneNumber,
        @Email(message = "Напишите действительный адрес электронной почты!")
        @NotBlank(message = "Почта не должна быть пустой")
        String email,
        @PasswordValid(message = "Длина пароля должна быть более 8 символов и содержать как минимум одну заглавную букву!")
        @NotBlank(message = "Пароль не должен быть пустым")
        String password,
        int age
) {

}
