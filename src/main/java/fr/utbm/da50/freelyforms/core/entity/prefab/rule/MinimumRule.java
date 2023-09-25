package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.*;

/**
 * Sets a minimum value for either a number, a date or a string length
 * @author Pourriture
 */
public class MinimumRule extends TypeRule {
    public MinimumRule(Object val) {
        super(val);
        associatedTypes.add(INTEGER);
        associatedTypes.add(FLOAT);
        associatedTypes.add(STRING);
        associatedTypes.add(DATE);
        associatedTypes.add(DATETIME);
        this.setName("MinimumRule");
    }


    @PersistenceCreator
    public MinimumRule(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        super(value, name, associatedTypes);
    }

    @Override
    public boolean verifyTypeRuleValidity() {
        // if value can be cast as a float, it is valid
        try {
            float val = Float.parseFloat(this.getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean verifyFormData(String data, Rule.FieldType fieldType) {
        try {
            float ruleValue = Float.parseFloat(this.getValue());
            switch (fieldType) {
                case INTEGER:
                case FLOAT:
                    float dataValue = Float.parseFloat(data);
                    if (dataValue < ruleValue)
                        return false;
                    else
                        return true;
                    // unreachable break statement
                case STRING:
                    if (data.length() < ruleValue)
                        return false;
                    else
                        return true;
                    // unreachable break statement
                case DATE, DATETIME:
                    // todo: add date, datetime support
                    return true;
                default:
                    // data not in associated types results in failure
                    return false;

            }
        } catch (Exception e) {
            return false;
        }
    }

    // Value is an integer (minimum or min string length)

}
