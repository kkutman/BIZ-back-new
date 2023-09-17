package kg.BIZ.repository;

import kg.BIZ.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByVolunteerId(Long id);
    List<Chat> findByManagerId(Long id);

}