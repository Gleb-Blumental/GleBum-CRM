package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WorkerRepository extends MongoRepository<Worker, String> {
    Optional<Worker> findByEmail(String email);
}
