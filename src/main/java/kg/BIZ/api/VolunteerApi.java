package kg.BIZ.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.BIZ.dto.request.AboutMeForVolunteerRequest;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.dto.response.VacancyRequestResponse;
import kg.BIZ.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
@RequiredArgsConstructor
@Tag(name = "Volunteer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VolunteerApi {

    private final VolunteerService volunteerService;

    @GetMapping
    public List<VacancyRequestResponse> getAllVolunteers(){
        return volunteerService.getAllVolunteers();
    }

    @PutMapping("/{volunteerId}/{vacancyId}")
    @Operation(summary = "accept vacancy", description = "This method for taking volunteer for company by manager!")
    public SimpleResponse acceptVacancy(@PathVariable Long volunteerId,@PathVariable Long vacancyId){
        return volunteerService.acceptVacancy(volunteerId, vacancyId);
    }

    @PutMapping("/aboutMe")
    @Operation(summary = "about me", description = "This method for adding some fields for volunteer")
    public SimpleResponse aboutMe(@RequestBody AboutMeForVolunteerRequest aboutMeForVolunteerRequest){
        return volunteerService.aboutMe(aboutMeForVolunteerRequest);
    }
}
