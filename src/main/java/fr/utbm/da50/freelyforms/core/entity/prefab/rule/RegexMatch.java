package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.STRING;

/**
 * checks whether a string matches a given regular expression
 * @author Pourriture
 */
public class RegexMatch extends TypeRule {
    public RegexMatch(Object val) {
        super(val);
        associatedTypes.add(STRING);
        this.setName("RegexMatch");

    }

    @PersistenceCreator
    public RegexMatch(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        super(value, name, associatedTypes);
    }

    /**
     * @return true if the value of the rule is correctly formatted
     */
    @Override
    public boolean verifyTypeRuleValidity() {
        return false;
    }

    /**
     * @param data      entered form data to validate
     * @param fieldType the actual type of the field
     * @return true if data/fieldtype are valid for this rule
     */
    @Override
    public boolean verifyFormData(String data, Rule.FieldType fieldType) {
        Pattern regexPattern = Pattern.compile(this.getValue());
        return regexPattern.matcher(data).find();
    }
}
