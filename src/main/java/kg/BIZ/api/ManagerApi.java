package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.BIZ.dto.request.AboutCompanyRequest;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Tag(name = "Manager API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManagerApi {
    private final ManagerService managerService;

    @PutMapping("/aboutCompany")
    @Operation(summary = "adding info", description = "This method for adding information   for company!")
    public SimpleResponse aboutCompany(@RequestBody AboutCompanyRequest aboutCompany){
        return managerService.aboutCompany(aboutCompany);
    }
}
