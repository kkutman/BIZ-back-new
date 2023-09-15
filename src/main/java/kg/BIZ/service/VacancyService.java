package kg.BIZ.service;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyResponse;
import kg.BIZ.model.Vacancy;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    SimpleResponse saveVacancy(VacancyRequest request);
    SimpleResponse updatedVacancy(VacancyRequest request,Long id);
    SimpleResponse deletedVacancy(Long id);
    VacancyResponse getById(Long id);
}
