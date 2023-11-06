package fr.utbm.da50.freelyforms.core.entity.prefab;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Random;

/**
 * The Field class represents the graphical fields present on the form.
 * It has a Rule attribute which validity it may verify.
 * The Field class may also verify that a given form data value is valid against its field using the Rule class.
 * Verification of valid form data values is done twice, in the Frontend app and in the backend
 * This is to catch invalid API form submissions as well as to check for duplicates.
 *
 * @author Pourriture
 */
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Jacksonized
public class Field {

    /** Name of the field. Should be unique within a group.
     */
    @Builder.Default
            @NonNull
    String name = "fieldName" + new Random().nextInt();

    /** Field display label on the frontend
     * */
    @Builder.Default
    @NonNull
    String label = "fieldLabel";

    /** Hint text within the field
     * */
    @Builder.Default
    @NonNull
    String caption = "fieldCaption";

    /**
     * Rules are used by the field to verify that data entered inside a field are valid
     */
    @Builder.Default
    @NonNull
    Rule rules = Rule.builder().build();



    /**
     * @return a stringified summary of the group
     * */
    public String inspect() {
        return "fieldname " + name + " label " + label + " rules " + rules.inspect();
    }

    /**
     * Verifies that a field is valid and that its rules are valid
     * @return true if the field is valid
     */
    public boolean verifyFieldValidity() {
        // verify that a field has its members and that its rule is correct

        boolean check = true;

        if (name == null || label == null || caption == null || rules == null)
            check = false;
        else if (rules != null)
            check = rules.verifyRuleValidity();

        return check;
    }

    /**
     * Calls the rule verification checks on data entered in a form.
     * @param data
     * @return true if the data is valid for this field
     */
    boolean validFieldValue (String data) {
        return this.rules.checkDataRules(data);
    }

}
