package kg.BIZ.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

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
        String description
) {
}
