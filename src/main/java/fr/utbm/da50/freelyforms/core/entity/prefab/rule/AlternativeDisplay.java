package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.TypeRuleFormDataException;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.*;

/**
 * Defines a variety of possible alternative displays for use with the frontend app.
 * This rule only defines expected frontend behavior and always verifies as true.
 *
 * @author Pourriture
 */
@NoArgsConstructor
public class AlternativeDisplay extends TypeRule {
    public AlternativeDisplay(Object val) {
        super(val);
        associatedTypes.add(SELECTOR);
        associatedTypes.add(BOOLEAN);
        associatedTypes.add(DATE);
        associatedTypes.add(DATETIME);
        associatedTypes.add(FLOAT);
        this.setName("AlternativeDisplay");

    }

    @PersistenceCreator
    public AlternativeDisplay(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        super(value, name, associatedTypes);
    }

    /**
     * Enum listing off current possible alternative displays
     * To add additional displays, simply add a new value here and modify the behavior of the frontend app accordingly
     */
    public enum AlternativeDisplays {
        DROPDOWN,
        CHECKBOX,
        RADIO,
        SLIDER,
        CALENDAR,
        MULTIPLE_CHOICE
    }

    /**
     * @return true if the value of the rule is correctly formatted
     */
    @Override
    public boolean verifyTypeRuleValidity() {
        boolean ans;
        String[] validDisplays = new String[]{"DROPDOWN", "CHECKBOX", "RADIO", "SLIDER", "CALENDAR", "MULTIPLE_CHOICE"};
        ArrayList<String> valid = new ArrayList<>(List.of(validDisplays));
        ans = valid.contains(this.getValue());
        return ans;
    }

    /**
     * @param data      entered form data to validate
     * @param fieldType the actual type of the field
     * No need to check this
     */
    @Override
    public void verifyFormData(Object data, Rule.FieldType fieldType) throws TypeRuleFormDataException {
    }
    // Value is a valid String from a list
}
