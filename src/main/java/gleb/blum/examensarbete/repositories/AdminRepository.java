package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> setRole(long id);
}