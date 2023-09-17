package kg.BIZ.service;

import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyRequestResponse;

import java.util.List;

public interface VolunteerService {
    List<VacancyRequestResponse> getAllVolunteers();
    SimpleResponse acceptVacancy(Long volunteerId, Long vacancyId);
}
