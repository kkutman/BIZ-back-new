package kg.BIZ.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_gen")
    @SequenceGenerator(name = "message_gen", sequenceName = "message_seq", allocationSize = 1)
    Long id;
    String message;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    Chat chat;
    boolean isManager;

    @ManyToOne(cascade = CascadeType.ALL)
    User sender;

    @ManyToOne(cascade = CascadeType.ALL)
    User recipient;
}
