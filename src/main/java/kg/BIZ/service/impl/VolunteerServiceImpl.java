package kg.BIZ.service.impl;

import jakarta.transaction.Transactional;
import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.request.AboutMeForVolunteerRequest;
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
import java.util.Iterator;
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

        for (Vacancy vacancy : vacancyRepository.findAllByUser(user)) {
            VacancyRequestResponse vacancyRequestResponse = new VacancyRequestResponse();
            vacancyRequestResponse.setVacancyId(vacancy.getId());
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

        Iterator<Volunteer> iterator = vacancy.getVolunteers().iterator();
        while (iterator.hasNext()) {
            Volunteer volunteer1 = iterator.next();
            if (volunteer1 == volunteer) {
                iterator.remove(); // Safely remove the element
                if (vacancy.getCountOfVolunteers() == 1) {
                    vacancyRepository.deleteById(vacancy.getId());
                } else {
                    vacancy.setCountOfVolunteers(vacancy.getCountOfVolunteers() - 1);
                }
            }
        }

        emailService.sendEmail(volunteer.getUser().getEmail(), "Вакансия!", "Успешно принято");

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("volunteer was successfully accepted").build();
    }

    @Override
    public SimpleResponse aboutMe(AboutMeForVolunteerRequest aboutMeForVolunteerRequest) {

        User user = jwtService.getAuthenticate();
        Volunteer volunteer = volunteerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Volunteer with ID not found "));

        volunteer.setLocation(aboutMeForVolunteerRequest.location());
        volunteer.setMotivation(aboutMeForVolunteerRequest.motivation());
        volunteer.setExperience(aboutMeForVolunteerRequest.experience());
        volunteer.setSkills(aboutMeForVolunteerRequest.skills());
        volunteer.setStrengths(aboutMeForVolunteerRequest.strengths());
        volunteer.setBusyness(aboutMeForVolunteerRequest.busyness());
        volunteer.setSocialMediaPages(aboutMeForVolunteerRequest.socialMediaPages());

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("volunteer was successfully updated").build();
    }
}
