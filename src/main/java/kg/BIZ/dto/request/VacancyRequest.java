package kg.BIZ.dto.request;

import kg.BIZ.model.enums.Navigation;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record VacancyRequest(
        String phoneNumber,
        boolean isBillable,
        String position,
        LocalDate startDate,
        LocalDate endDate,

        String requirement,
        String location,
        int countOfVolunteers,
        String description,
        Navigation navigation
) {
}
