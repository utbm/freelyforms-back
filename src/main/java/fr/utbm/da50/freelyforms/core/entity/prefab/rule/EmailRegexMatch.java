package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.STRING;

/**
 * RegexMatch rule with predefined value. This value is a regex designed to match email addresses
 */
public class EmailRegexMatch extends RegexMatch{

    public EmailRegexMatch(){
        super("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        associatedTypes.add(STRING);
        this.setName("RegexMatch");
    }

    @PersistenceCreator
    public EmailRegexMatch(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        super(value, name, associatedTypes);
    }
}
