package kg.BIZ.dto.response;

import lombok.Builder;

@Builder
public record VacancyResponse(
        String companyName,
        String description,
        String email,
        String location,
        String phoneNumber
) {
}
