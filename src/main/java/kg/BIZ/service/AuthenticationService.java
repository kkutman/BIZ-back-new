package kg.BIZ.service;


import kg.BIZ.dto.request.AuthenticateRequest;
import kg.BIZ.dto.request.RegisterRequest;
import kg.BIZ.dto.response.AuthenticationResponse;
import kg.BIZ.model.User;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticateRequest request);
    User getAuthenticate();
}
