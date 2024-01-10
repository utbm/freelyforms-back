package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.TypeRuleFormDataException;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.*;

/**
 * Sets a minimum value for either a number, a date or a string length
 * @author Pourriture
 */
@NoArgsConstructor
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
    public void verifyFormData(Object data, Rule.FieldType fieldType) throws TypeRuleFormDataException {

            switch (fieldType) {
                case INTEGER:
                    int intRuleValue = Integer.parseInt(getValue());
                    int intValue = (Integer) data;
                    if (intValue < intRuleValue)
                        throw new TypeRuleFormDataException("Data value ("+ intValue +") < Rule value (" + intRuleValue + ")");
                    break;
                case FLOAT:
                    float floatRuleValue = Float.parseFloat(this.getValue());
                    float dataValue = (Float) data;
                    if (dataValue < floatRuleValue)
                        throw new TypeRuleFormDataException("Data value ("+ dataValue +") < Rule value (" + floatRuleValue + ")");
                    break;
                case STRING:
                    int stringRuleValue = Integer.parseInt(getValue());
                    String stringValue = (String) data;
                    if (stringValue.length() < stringRuleValue)
                        throw new TypeRuleFormDataException("Data length < Rule value");
                    break;
                case DATE, DATETIME:
                    LocalDateTime dateTimeRuleValue = LocalDateTime.parse(getValue());
                    LocalDateTime dateTimeValue = LocalDateTime.parse((String) data);
                    if (dateTimeValue.isBefore(dateTimeRuleValue))
                        throw new TypeRuleFormDataException("Data value (" + dateTimeValue + ") is before Rule value (" + dateTimeRuleValue + ")");
                    break;
                default:
                    throw new TypeRuleFormDataException("Data is not in associated types of the rule");

            }
    }

    // Value is an integer (minimum or min string length)

}
