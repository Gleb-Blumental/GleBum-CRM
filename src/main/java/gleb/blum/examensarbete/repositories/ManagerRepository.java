package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Manager, String> {
    Optional<Manager> findByEmail(String email);
}