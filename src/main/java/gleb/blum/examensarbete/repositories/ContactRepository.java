package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact, String> {
    List<Contact> findByManagesId(String managesId);
}