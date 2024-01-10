package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.TypeRuleFormDataException;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.STRING;

/**
 * checks whether a string matches a given regular expression
 * 
 * @author Pourriture
 */
@NoArgsConstructor
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
        try {
            Pattern.compile(this.getValue());
            return true;
        } catch (PatternSyntaxException exception) {
            System.err.println(exception.getDescription());
            return false;
        }
    }

    /**
     * @param data      entered form data to validate
     * @param fieldType the actual type of the field
     * @return true if data/fieldtype are valid for this rule
     */
    @Override
    public void verifyFormData(String data, Rule.FieldType fieldType) throws TypeRuleFormDataException {
        Pattern regexPattern = Pattern.compile(this.getValue());
        if (!regexPattern.matcher(data).find())
            throw new TypeRuleFormDataException("Data does not match the regex");
    }
}
