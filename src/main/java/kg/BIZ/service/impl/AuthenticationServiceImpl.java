package kg.BIZ.service.impl;

import jakarta.transaction.Transactional;
import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.request.AuthenticateRequest;
import kg.BIZ.dto.request.RegisterRequest;
import kg.BIZ.dto.response.AuthenticationResponse;
import kg.BIZ.exception.exceptions.*;
import kg.BIZ.model.Manager;
import kg.BIZ.model.User;
import kg.BIZ.model.Volunteer;
import kg.BIZ.model.enums.Role;
import kg.BIZ.repository.UserRepository;
import kg.BIZ.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        System.out.println("login : "+login);
        log.info("Токен взят!");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("Пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь!");
            return new NotFoundException("пользователь не найден с токеном пожалуйста войдите или зарегистрируйтесь");
        });
    }
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.error(String.format("Пользователь с адресом электронной почты %s уже существует", request.email()));
            throw new AlreadyExistException(String.format("Пользователь с адресом электронной почты %s уже существует", request.email()));
        }
        String split = request.email().split("@")[0];
        if (split.equals(request.password())) {
            throw new BadRequestException("Создайте более надежный пароль");
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role()))
                .createdAt(LocalDate.now())
                .build();

        if (request.role().equalsIgnoreCase(Role.VOLUNTEER.name())){
            user.setVolunteer(Volunteer.builder().age(request.age()).user(user).build());
        }else if (request.role().equalsIgnoreCase(Role.MANAGER.name())){
            user.setManager(Manager.builder().user(user).build());
        }

        userRepository.save(user);
        log.info(String.format("Пользователь %s успешно сохранен!", user.getEmail()));
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.error(String.format("Пользователь с адресом электронной почты %s не существует", request.email()));
                    return new BadCredentialException(String.format("Пользователь с адресом электронной почты %s не существует", request.email()));
                });
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.error("Пароль не подходит");
            throw new BadRequestException("Пароль не подходит");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        log.info(String.format("Пользователь %s успешно аутентифицирован", user.getEmail()));
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }
}
