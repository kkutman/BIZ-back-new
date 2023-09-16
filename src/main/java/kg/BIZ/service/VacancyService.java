package kg.BIZ.service;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.ResponseVacancy;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyResponse;
import java.util.List;

public interface VacancyService {
    SimpleResponse saveVacancy(VacancyRequest request);
    SimpleResponse updatedVacancy(VacancyRequest request,Long id);
    SimpleResponse deletedVacancy(Long id);
    VacancyResponse getById(Long id);
    List<ResponseVacancy> getAllVacancy();
}
