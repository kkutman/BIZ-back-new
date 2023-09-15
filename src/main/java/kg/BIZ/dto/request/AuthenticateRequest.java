package kg.BIZ.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthenticateRequest(
        @NotBlank(message = "Электронная почта не должна быть пустой!")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым!")
        String password
) {
}