package kg.BIZ.dto.request;
import java.time.LocalDate;

public record AboutCompanyRequest(
        String directorOfCompany,
        String companySlogan,
        LocalDate creatingYear,
        String scopeOfWork,
        String description
) {
}
