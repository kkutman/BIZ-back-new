package kg.BIZ.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Tag(name = "Email")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailApi {
    private final EmailService emailService;

    @PostMapping
    public SimpleResponse emailSend(@RequestParam String to,@RequestParam String subject,@RequestParam String text){
        return emailService.sendEmail(to,subject,text);
    }
}
