package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.CRM;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CRMRepository extends MongoRepository<CRM, String> {
    Optional<CRM> findByWebsiteName(String websiteName);
}