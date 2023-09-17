package kg.BIZ.repository;

import kg.BIZ.model.User;
import kg.BIZ.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy,Long> {
    List<Vacancy> findAllByUser(User user);
}
