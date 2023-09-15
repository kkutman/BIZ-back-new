package kg.BIZ.service;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.SimpleResponse;
import org.springframework.security.core.Authentication;

public interface VacancyService {
    SimpleResponse saveVacancy(VacancyRequest request, Authentication authentication);
    SimpleResponse updatedVacancy(VacancyRequest request,Long id);
    SimpleResponse deletedVacancy(Long id);
}
