package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.BIZ.dto.request.VacancyRequest;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacancy")
@RequiredArgsConstructor
@Tag(name = "Vacancy API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VacancyApi {
    private final VacancyService vacancyService;
    @PostMapping
    @Operation(summary = "Save vacancy", description = "This method save vacancy!")
    public SimpleResponse saveBanner(@RequestBody @Valid VacancyRequest request, Authentication authentication) {
        return vacancyService.saveVacancy(request, authentication);
    }
}
