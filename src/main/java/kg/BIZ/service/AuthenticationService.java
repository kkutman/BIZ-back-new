package kg.BIZ.service;


import kg.BIZ.dto.request.AuthenticateRequest;
import kg.BIZ.dto.request.CompanyRegisterRequest;
import kg.BIZ.dto.request.VolunteerRegisterRequest;
import kg.BIZ.dto.response.AuthenticationResponse;
import kg.BIZ.model.User;

public interface AuthenticationService {
    AuthenticationResponse volunteerRegister(VolunteerRegisterRequest request);
    AuthenticationResponse companyRegister(CompanyRegisterRequest companyRegisterAuthRequest);
    AuthenticationResponse authenticate(AuthenticateRequest request);
    User getAuthenticate();
}
