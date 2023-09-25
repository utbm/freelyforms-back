package fr.utbm.da50.freelyforms.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User class that may be used to identify and authenticate users
 *
 * @author hamza91
 */

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
    public class User {

        @Id
    private String id;
    private String lastname;
    private String firstname;
    private Long age;

}