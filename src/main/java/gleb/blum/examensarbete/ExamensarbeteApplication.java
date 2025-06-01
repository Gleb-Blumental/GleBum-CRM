package gleb.blum.examensarbete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ExamensarbeteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamensarbeteApplication.class, args);
	}

}
