package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @SequenceGenerator(name = "manger_gen", sequenceName = "manager_seq",
            allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manager_gen")
    Long id;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    User user;
}