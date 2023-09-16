package kg.BIZ.service.impl;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.ResponseVacancy;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.User;
import kg.BIZ.model.Vacancy;
import kg.BIZ.repository.UserRepository;
import kg.BIZ.repository.VacancyRepository;
import kg.BIZ.service.AuthenticationService;
import kg.BIZ.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    public User getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("Токен взят!");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("Пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь!");
            return new NotFoundException("пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь");
        });
    }

    @Override
    public SimpleResponse saveVacancy(VacancyRequest request) {
        User user = getAuthenticate();
        Vacancy vacancy = Vacancy.builder()
                .companyName(request.companyName())
                .phoneNumber(request.phoneNumber())
                .createdAt(LocalDate.now())
                .requirement(request.requirement())
                .location(request.location())
                .isActive(false)
                .user(user)
                .email(user.getEmail())
                .build();
        vacancyRepository.save(vacancy);
        return new SimpleResponse(HttpStatus.OK, "Vacancy successfully saved");
    }

    @Override
    public SimpleResponse updatedVacancy(VacancyRequest request, Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id)));
        if (request.companyName() != null) vacancy.setCompanyName(request.companyName());
        if (request.requirement() != null) vacancy.setRequirement(request.requirement());
        if (request.phoneNumber() != null) vacancy.setPhoneNumber(request.phoneNumber());
        if (request.location() != null) vacancy.setLocation(request.location());
        vacancyRepository.save(vacancy);
        return new SimpleResponse(HttpStatus.OK, "Vacancy successfully updated!");
    }

    @Override
    public SimpleResponse deletedVacancy(Long id) {
        vacancyRepository.delete(vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id))));
        return new SimpleResponse(HttpStatus.OK, "Vacancy successfully deleted!");
    }

    @Override
    public VacancyResponse getById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id)));
        return VacancyResponse.builder()
                .companyName(vacancy.getCompanyName())
                .description(vacancy.getRequirement())
                .email(vacancy.getEmail())
                .location(vacancy.getLocation())
                .phoneNumber(vacancy.getPhoneNumber())
                .build();
    }

    @Override
    public List<ResponseVacancy> getAllVacancy(Boolean trueOrFalse) {
        List<ResponseVacancy> getAllVacancy = new ArrayList<>();
        List<Vacancy> vacancies = vacancyRepository.findAll();
        for (Vacancy vacancy : vacancies) {
            if (vacancy.isActive()==trueOrFalse) {
                getAllVacancy.add(ResponseVacancy.builder()
                                .id(vacancy.getId())
                                .aboutVacancy(vacancy.getRequirement())
                                .date(vacancy.getCreatedAt())
                                .companyName(vacancy.getCompanyName())
                                .build());
            }
        }
        return getAllVacancy;
    }

    @Override
    public SimpleResponse acceptRequest(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id)));
        if (!vacancy.isActive()) {
            vacancy.setActive(true);
            vacancyRepository.save(vacancy);
        }
        return new SimpleResponse(HttpStatus.OK, "Request successfully accepted!");
    }

    @Override
    public SimpleResponse respond(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id)));
        if (vacancy.getVolunteers() == null) {
            vacancy.setVolunteers(new ArrayList<>());
        }
        vacancy.getVolunteers().add(getAuthenticate().getVolunteer());
        vacancyRepository.save(vacancy);
        return new SimpleResponse(HttpStatus.OK, "Respond successfully");
    }
}
