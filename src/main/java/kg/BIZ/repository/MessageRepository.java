package kg.BIZ.repository;

import kg.BIZ.model.Chat;
import kg.BIZ.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
}