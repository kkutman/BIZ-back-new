package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "volunteer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer {
    @Id
    @SequenceGenerator(name = "volunteer_gen", sequenceName = "volunteer_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "volunteer_gen")
    Long id;

    int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "volunteer")
    List<Chat> chats;

}
