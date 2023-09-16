package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.ResponseVacancy;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyResponse;
import kg.BIZ.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancy")
@RequiredArgsConstructor
@Tag(name = "Vacancy API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VacancyApi {
    private final VacancyService vacancyService;

    @PostMapping
    @Operation(summary = "Save vacancy", description = "This method save vacancy!")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public SimpleResponse saveVacancy(@RequestBody @Valid VacancyRequest request) {
        return vacancyService.saveVacancy(request);
    }

    @PutMapping
    @Operation(summary = "Updated vacancy", description = "This method updated vacancy")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public SimpleResponse updatedVacancy(@RequestParam Long vacancyId, @RequestBody @Valid VacancyRequest request) {
        return vacancyService.updatedVacancy(request, vacancyId);
    }

    @DeleteMapping
    @Operation(summary = "Deleted vacancy", description = "This method deleted vacancy")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public SimpleResponse deletedVacancy(@RequestParam Long vacancyId) {
        return vacancyService.deletedVacancy(vacancyId);
    }

    @GetMapping
    @Operation(summary = "Get by vacancy", description = "This method get by id vacancy")
    public VacancyResponse getById(@RequestParam Long vacancyId) {
        return vacancyService.getById(vacancyId);
    }

    @GetMapping("/get_all")
    @Operation(summary = "Get all vacancy", description = "This method get all vacancy")
    public List<ResponseVacancy> getAll() {
        return vacancyService.getAllVacancy();
    }

    @PutMapping("/accept")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Accept request", description = "This method accept request")
    public SimpleResponse acceptRequest(@RequestParam Long vacancyId) {
        return vacancyService.acceptRequest(vacancyId);
    }

}
