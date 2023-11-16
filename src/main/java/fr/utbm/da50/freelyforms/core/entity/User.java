package fr.utbm.da50.freelyforms.core.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * User class that may be used to identify and authenticate users
 *
 * @author hamza91
 */

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Builder.Default
    @NonNull
    private String id = UUID.randomUUID().toString().split("-")[0];

    @Builder.Default
    @NonNull
    private String lastname = "";

    @Builder.Default
    @NonNull
    private String firstname = "";

    @Builder.Default
    @NonNull
    private Long age = 1L;

}