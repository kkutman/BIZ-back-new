package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manager")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager {
    @Id
    @SequenceGenerator(name = "manager_gen", sequenceName = "manager_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manager_gen")
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "manager")
    List<Chat> chats;
    String directorOfCompany;
    String address;
    String companyName;
    String companySlogan;
    LocalDate creatingYear;
    String sphereOfActivity;
    String description;
    String instagram;
    String whatsapp;
    String telegram;

    //fields for images will be added
}
