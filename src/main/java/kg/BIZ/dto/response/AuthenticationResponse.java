package kg.BIZ.dto.response;

import kg.BIZ.model.enums.Role;
import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String email,
        Role role,
        String token
) {
}