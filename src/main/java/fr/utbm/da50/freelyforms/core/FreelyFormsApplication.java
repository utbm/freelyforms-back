package fr.utbm.da50.freelyforms.core;



import fr.utbm.da50.freelyforms.core.repository.PrefabRepository;
import fr.utbm.da50.freelyforms.core.service.FormDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FreelyFormsApplication implements CommandLineRunner {

    @Autowired
    PrefabRepository prefabRepo;

    @Autowired
    FormDataService dataService;

    // Run this to start the app
    public static void main(String[] args) {
        SpringApplication.run(FreelyFormsApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {

    }
}
