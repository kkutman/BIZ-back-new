package kg.BIZ.service.impl;

import kg.BIZ.config.jwt.JwtService;
import kg.BIZ.dto.request.AboutCompanyRequest;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.exception.exceptions.NotFoundException;
import kg.BIZ.model.Manager;
import kg.BIZ.model.User;
import kg.BIZ.repository.ManagerRepository;
import kg.BIZ.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final JwtService jwtService;
    private final ManagerRepository managerRepository;

    @Override
    public SimpleResponse aboutCompany(AboutCompanyRequest aboutCompany) {
        User user = jwtService.getAuthenticate();
        Manager manager = managerRepository.findByUserId(user.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Company with user id %s not found!", user.getId())));

        manager.setDirectorOfCompany(aboutCompany.directorOfCompany());
        manager.setCompanySlogan(aboutCompany.companySlogan());
        manager.setCreatingYear(aboutCompany.creatingYear());
        manager.setScopeOfWork(aboutCompany.scopeOfWork());
        manager.setDescription(aboutCompany.description());

        managerRepository.save(manager);

        return new SimpleResponse(HttpStatus.OK, "Company fields successfully saved!");
    }
}











