package kg.BIZ.service.impl;

import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.SimpleResponse;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public User getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
        String login = authentication.getName();
        System.out.println("login : "+login);
        log.info("Токен взят!");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("Пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь!");
            return new NotFoundException("пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь");
        });
    }
    @Override
    public SimpleResponse saveVacancy(VacancyRequest request, Authentication authentication) {
        System.out.println("name "+getAuthenticate().getEmail());

        User principal = (User) authentication.getPrincipal();
        System.out.println(principal.getUsername());

        Vacancy vacancy = Vacancy.builder()
                .companyName(request.companyName())
                .phoneNumber(request.phoneNumber())
                .createdAt(LocalDate.now())
                .requirement(request.requirement())
                .isActive(false)
                .build();
        vacancyRepository.save(vacancy);
        return new SimpleResponse(HttpStatus.OK,"Vacancy successfully saved");
    }

    @Override
    public SimpleResponse updatedVacancy(VacancyRequest request,Long id) {
//        Vacancy vacancy = vacancyRepository.findById(id)


        return null;
    }

    @Override
    public SimpleResponse deletedVacancy(Long id) {
        return null;
    }
}
