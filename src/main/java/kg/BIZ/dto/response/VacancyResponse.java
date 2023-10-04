package kg.BIZ.dto.response;

import kg.BIZ.model.enums.Navigation;
import lombok.Builder;

@Builder
public record VacancyResponse(
        Long id,
        Long managerId,
        String companyName,
        String description,
        String email,
        String location,
        String phoneNumber,
        Navigation navigation
) {
}
