package fr.utbm.da50.freelyforms.core.startup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.entity.User;
import fr.utbm.da50.freelyforms.core.service.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class InitializeData {

    @Autowired
    private MongoTemplate mongoTemplate;


    @PostConstruct
    public void init() throws IOException {
        System.out.println("Initializing data");

        ObjectMapper objectMapper = new ObjectMapper();
//        List<User> persons = objectMapper.readValue(
//                new ClassPathResource("/data/users.json").getFile(),
//                new TypeReference<List<User>>() {
//                });

        Resource resource = new ClassPathResource("/data/users.json");
        try (InputStream inputStream = resource.getInputStream()) {
            List<User> persons = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});
            // Insert the data into the database
            mongoTemplate.insertAll(persons);

            System.out.println("Data initialization completed successfully");
        } catch (IOException e) {
            // Handle the IOException
            System.out.println("An error occured during the data initialization");
        }

        Prefab personPrefab = Generator.generatePerson();
        Prefab streetsPrefab = Generator.generateStreet();
        FormData personFormData = Generator.generateFormData(personPrefab);
        FormData streetsFormData = Generator.generateFormData(streetsPrefab);

        mongoTemplate.insert(personPrefab);
        mongoTemplate.insert(streetsPrefab);
        mongoTemplate.insert(personFormData);
        mongoTemplate.insert(streetsFormData);

    }
}
