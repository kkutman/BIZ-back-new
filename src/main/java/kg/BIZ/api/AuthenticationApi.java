package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import kg.BIZ.dto.request.AuthenticateRequest;
import kg.BIZ.dto.request.CompanyRegisterRequest;
import kg.BIZ.dto.request.VolunteerRegisterRequest;
import kg.BIZ.dto.response.AuthenticationResponse;
import kg.BIZ.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "This method validates the request and creates a new user.")
    @PostMapping("/volunteer/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Valid VolunteerRegisterRequest request) {
        return authenticationService.volunteerRegister(request);
    }

    @Operation(summary = "Register a new company", description = "This method validates the request and creates a new company.")
    @PostMapping("/company/sign-up")
    public AuthenticationResponse companySignUp(@RequestBody @Valid CompanyRegisterRequest companyRegisterAuthRequest){
        return authenticationService.companyRegister(companyRegisterAuthRequest);
    }

    @Operation(summary = "Authenticate a user", description = "This method validates the request and authenticates a user.")
    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticateRequest request) {
        return authenticationService.authenticate(request);
    }

}
