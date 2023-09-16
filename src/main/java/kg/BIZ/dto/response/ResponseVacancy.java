package kg.BIZ.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResponseVacancy(
        Long id,
        String companyName,
        String aboutVacancy,
        LocalDate date
) {
}
