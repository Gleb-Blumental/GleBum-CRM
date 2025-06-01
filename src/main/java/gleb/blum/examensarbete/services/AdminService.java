package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Admin;
import gleb.blum.examensarbete.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(String id) {
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(String id, Admin adminDetails) {
        Admin existingAdmin = adminRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));

        existingAdmin.setName(adminDetails.getName());
        existingAdmin.setEmail(adminDetails.getEmail());

        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(String id) {
        Admin admin = adminRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        adminRepository.delete(admin);
    }
}
