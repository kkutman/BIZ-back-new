package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancy")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vacancy {

    @Id
    @SequenceGenerator(name = "vacancy_gen", sequenceName = "vacancy_seq",
            allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_gen")
    Long id;

    String requirement;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    @Column(name = "created_at")
    LocalDate createdAt;

    @ManyToOne
    User user;

    boolean isActive;
}
