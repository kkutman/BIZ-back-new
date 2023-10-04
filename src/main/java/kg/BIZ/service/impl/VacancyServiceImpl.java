package kg.BIZ.service.impl;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.ResponseVacancy;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.Manager;
import kg.BIZ.model.User;
import kg.BIZ.model.Vacancy;
import kg.BIZ.model.Volunteer;
import kg.BIZ.repository.UserRepository;
import kg.BIZ.repository.VacancyRepository;
import kg.BIZ.repository.VolunteerRepository;
import kg.BIZ.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final VolunteerRepository volunteerRepository;
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
        Manager manager = user.getManager();

        Vacancy vacancy = Vacancy.builder()
                .companyName(manager.getCompanyName())
                .phoneNumber(request.phoneNumber())
                .createdAt(LocalDate.now())
                .requirement(request.requirement())
                .location(request.location())
                .countOfVolunteers(request.countOfVolunteers())
                .isActive(false)
                .isBillable(request.isBillable())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .description(request.description())
                .position(request.position())
                .navigation(request.navigation())
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
        if (request.requirement() != null) vacancy.setRequirement(request.requirement());
        if (request.phoneNumber() != null) vacancy.setPhoneNumber(request.phoneNumber());
        if (request.location() != null) vacancy.setLocation(request.location());
        if (request.countOfVolunteers() != 0) vacancy.setCountOfVolunteers(request.countOfVolunteers());
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
                .id(vacancy.getId())
                .managerId(vacancy.getUser().getManager().getId())
                .companyName(vacancy.getCompanyName())
                .description(vacancy.getRequirement())
                .email(vacancy.getEmail())
                .location(vacancy.getLocation())
                .navigation(vacancy.getNavigation())
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
        User user = getAuthenticate();
        Volunteer volunteer1 = volunteerRepository.findByUserId(user.getId()).orElseThrow(
                () -> new NotFoundException(String.format("Volunteer with id %s not found!", id)));

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Vacancy with id %s not found!", id)));

        if (vacancy.getVolunteers() == null || !vacancy.getVolunteers().contains(volunteer1)) {
            vacancy.addVolunteer(volunteer1);
            vacancyRepository.save(vacancy);
            return new SimpleResponse(HttpStatus.OK, "Respond successfully");
        } else {
            return new SimpleResponse(HttpStatus.BAD_REQUEST, "уже сохранен!");
        }
    }

}
