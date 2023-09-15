package kg.BIZ.dto.request;

import lombok.Builder;

@Builder
public record VacancyRequest(
        String companyName,
        String phoneNumber,
        String requirement
) {
}
