package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.exception.formdata.FieldNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.formdata.GroupNameNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.prefab.NoExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.TypeRuleFormDataException;
import fr.utbm.da50.freelyforms.core.service.FormDataService;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;
import java.util.List;

import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.SELECTOR;

/**
 * Determines a prefab.group.field combination that is to be used as selector values for this prefab.
 * That is to say, the selector should, instead of only offering and accepting static data values, accept any value that
 * has already been entered into another form, in a specific field determined by the value of this rule.
 *  value should be a string formatted prefabName.groupname.fieldName
 *
 * When such a rule is encountered by the front-end app, it should query the /api/formdata/{form}/{group}/{field} route
 * for a list of values matching the rule and displays them as choices to select for the end user
 *
 * @author Pourriture
 */
@NoArgsConstructor
public class SelectDataSet extends TypeRule {
    public SelectDataSet(Object val) {
        super(val);
        associatedTypes.add(SELECTOR);
        this.setName("SelectDataSet");

    }

    @PersistenceCreator
    public SelectDataSet(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        super(value, name, associatedTypes);
    }

    /**
     * @return true if the value of the rule is correctly formatted (in a format of prefab.group.field)
     */
    @Override
    public boolean verifyTypeRuleValidity() {
        if (this.getValue().split("\\.").length == 3){
            return true;
        }
        return false;
    }

    /**
     * @param data      entered form data to validate
     * @param fieldType the actual type of the field
     */
    @Override
    public void verifyFormData(Object data, Rule.FieldType fieldType) throws TypeRuleFormDataException {
        if (!verifyTypeRuleValidity()) {
            throw new TypeRuleFormDataException("Type rule is not valid");
        }
        FormDataService service = new FormDataService();
        String[] split = this.getValue().split("\\.");
        List<Object> values;
        try {
            values = service.getAllFormDataFromPrefabField(split[0], split[1], split[2]);
        } catch (NoExistingPrefabException | GroupNameNotFoundException | FieldNotFoundException e) {
            throw new TypeRuleFormDataException("Exception in getAllFormDataFromPrefab Field : " + e.getMessage());
        }
        if (!values.contains(data)) {
            throw new TypeRuleFormDataException("form data value " + data + " not in data set " + values);
        }

    }
    // Value is a String representing a form.group.field combination?
}
