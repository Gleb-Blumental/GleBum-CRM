package gleb.blum.examensarbete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import gleb.blum.examensarbete.repository.CRMRepository;
import gleb.blum.examensarbete.repository.AdminRepository;
import gleb.blum.examensarbete.models.CRM;
import gleb.blum.examensarbete.models.Admin;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CRMRepository crmRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public DataInitializer(CRMRepository crmRepository, AdminRepository adminRepository) {
        this.crmRepository = crmRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize CRM if not exists
        if (crmRepository.count() == 0) {
            CRM crm = new CRM();
            crm.setWebsiteName("CRM System");
            crm.setWebsiteUrl("https://crm.example.com");
            crmRepository.save(crm);
        }

        // Initialize admin if not exists
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setName("System Administrator");
            admin.setEmail("admin@example.com");
            admin.setRole("SUPER_ADMIN");
            adminRepository.save(admin);
        }
    }
}
