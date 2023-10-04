package kg.BIZ.dto.request;
import java.time.LocalDate;

public record AboutCompanyRequest(
        String directorOfCompany,
        String address,
        String companySlogan,
        LocalDate creatingYear,
        String sphereOfActivity,
        String instagram,
        String whatsapp,
        String telegram,
        String description

        //images fields will be added
) {
}
