package kg.BIZ.api;

import io.swagger.v3.oas.annotations.tags.Tag;
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
    public SimpleResponse acceptVacancy(@PathVariable Long volunteerId,@PathVariable Long vacancyId){
        return volunteerService.acceptVacancy(volunteerId, vacancyId);
    }

}
