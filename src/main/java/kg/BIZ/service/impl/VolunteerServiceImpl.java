package kg.BIZ.service.impl;

import jakarta.transaction.Transactional;
import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyRequestResponse;
import kg.BIZ.dto.response.VolunteerRequestResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.User;
import kg.BIZ.model.Vacancy;
import kg.BIZ.model.Volunteer;
import kg.BIZ.repository.VacancyRepository;
import kg.BIZ.repository.VolunteerRepository;
import kg.BIZ.service.EmailService;
import kg.BIZ.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VolunteerServiceImpl implements VolunteerService {
    private final VacancyRepository vacancyRepository;
    private final VolunteerRepository volunteerRepository;

    private final EmailService emailService;
    private final JwtService jwtService;

    @Override
    public List<VacancyRequestResponse> getAllVolunteers() {
        User user = jwtService.getAuthenticate();

        List<VacancyRequestResponse> vacancyRequestResponses = new ArrayList<>();

        for (Vacancy vacancy : user.getVacancy()) {
            VacancyRequestResponse vacancyRequestResponse = new VacancyRequestResponse();
            vacancyRequestResponse.setVacancyName(vacancy.getCompanyName());

            List<VolunteerRequestResponse> volunteerRequestResponses = new ArrayList<>(); // Define a new list

            for (Volunteer volunteer : vacancy.getVolunteers()) {
                VolunteerRequestResponse volunteerRequestResponse = new VolunteerRequestResponse();
                volunteerRequestResponse.setId(volunteer.getId());
                volunteerRequestResponse.setFullName(volunteer.getUser().getFirstName() + " " + volunteer.getUser().getLastName());
                volunteerRequestResponse.setAge(volunteer.getAge());
                volunteerRequestResponse.setEmail(volunteer.getUser().getEmail());
                volunteerRequestResponse.setPhoneNumber(volunteer.getUser().getPhoneNumber());

                volunteerRequestResponses.add(volunteerRequestResponse);
            }

            vacancyRequestResponse.setVolunteerRequestResponses(volunteerRequestResponses);
            vacancyRequestResponses.add(vacancyRequestResponse);
        }

        return vacancyRequestResponses;
    }


    @Override
    public SimpleResponse acceptVacancy(Long volunteerId, Long vacancyId) {

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Manager with ID not found "));

        vacancy.addVolunteer(volunteer);
        vacancyRepository.save(vacancy);

        emailService.sendEmail(volunteer.getUser().getEmail(), "Вакансия!","Успешно принято");

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("volunteer was successfully accepted").build();
    }


}
