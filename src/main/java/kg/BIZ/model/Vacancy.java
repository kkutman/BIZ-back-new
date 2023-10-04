package kg.BIZ.model;

import jakarta.persistence.*;
import kg.BIZ.model.enums.Navigation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            allocationSize = 1, initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancy_gen")
    Long id;

    String requirement;

    String location;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    @Column(name = "created_at")
    LocalDate createdAt;

    String position;
    boolean isBillable;
    String description;

    LocalDate startDate;
    LocalDate endDate;

    @ManyToOne
    User user;

    boolean isActive;
    int countOfVolunteers;
    @Enumerated(EnumType.STRING)
    Navigation navigation;
    @OneToMany
    List<Volunteer>volunteers;

    public void addVolunteer(Volunteer volunteer) {
        if (this.volunteers == null) {
            this.volunteers = new ArrayList<>();
        }
        this.volunteers.add(volunteer);
    }
}
