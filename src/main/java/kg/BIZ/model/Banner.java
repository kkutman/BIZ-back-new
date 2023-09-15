package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banners")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Banner {
    @Id
    @SequenceGenerator(name = "banner_gen", sequenceName = "banner_seq",
            allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_gen")
    Long id;

    String image;
}
