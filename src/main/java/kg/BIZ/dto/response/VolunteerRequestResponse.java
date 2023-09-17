package kg.BIZ.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRequestResponse {
    private Long id;
    private String fullName;
    private int age;
    private String email;
    private String phoneNumber;
}
