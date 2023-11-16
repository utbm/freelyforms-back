package fr.utbm.da50.freelyforms.core.entity.prefab;

import fr.utbm.da50.freelyforms.core.entity.prefab.rule.TypeRule;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.RuleException;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.TypeRuleFormDataException;
import lombok.*;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

/** The Rule class holds attributes and methods used to define the function and behavior of a field, such as accepted data type or optionality.
 * Its TypeRule attribute holds additional rules depending on type, such as the maximum length of a string in the field or an alternative display for certain fields.
 *
 * @author Pourriture
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Jacksonized
@NonFinal
public class Rule {
    /**
     * Enumeration representing the possible data types
     */
    public enum FieldType {
        INTEGER,
        STRING,
        FLOAT,
        DATE,
        DATETIME,
        BOOLEAN,
        SELECTOR

    }

    /** Whether entering data in the field is optional
     *
     */
    @Field
    @Builder.Default
    @NonFinal
    private boolean optional = false;
    /**
     * Which groups and fields (in the group or group.field format) this rule excludes from being filled in if its field has a value
     */
    @Field
    @Builder.Default
    @NonFinal
    private ArrayList<String> excludes = new ArrayList<>();
    /**
     * Rules defining  verifications depending on fieldType and the specific class inherited from abstract class TypeRule in the array
     */
    @Field
    @Builder.Default
    @NonFinal
    private ArrayList<TypeRule> typeRules = new ArrayList<>();
    /**
     * Whether the field will be hidden on the front-end app
     */
    @Field
    @Builder.Default
    @NonNull
    boolean hidden = false;
    /**
     * Possible list of selector values, such as a dropdown or a list selectable with a radio-button-style array (only for use with the SELECTOR fieldType)
     */
    @Field
    @Builder.Default
    @NonNull
    private ArrayList<String> selectorValues = new ArrayList<>();
    /**
     * Data type for the field value
     */
    @Field
    @Builder.Default
    @NonNull
    private FieldType fieldType = FieldType.INTEGER;
    // Test constructor
    public Rule(FieldType fieldType) {
        this.fieldType = fieldType;
        this.optional = false;
        this.excludes = new ArrayList<>();
        this.hidden = false;
        this.selectorValues = new ArrayList<>();
        this.typeRules = new ArrayList<>();

    }


    /**
     * @param typeRule to be added to the rules, if it is valid
     */
    public void addTypeRule(TypeRule typeRule) {
        if (typeRule.matchesFieldTypes(this.fieldType) && typeRule.verifyTypeRuleValidity()) {
            this.typeRules.add(typeRule);
        }
    }

    /**
     * @return a stringified summary of the rule
     */
    public String inspect(){
        StringBuilder ret = new StringBuilder("{ " + fieldType + " optional " + optional + " hidden " + hidden + " excludes " + excludes + " typeRules ");
        for (TypeRule rule : typeRules) {
            ret.append(rule.inspect());
        }
        ret.append(" }");
        return ret.toString();
    }

    /**
     *
     * @return whether the rule is valid and that its enforced type matches type-specific rules
     */
    public boolean verifyRuleValidity() {
        boolean check = true;
        for (TypeRule rule : typeRules) {
            if (!rule.verifyTypeRuleValidity()) {
                System.out.println("Rule invalid:" + rule + rule.getValue());
                check = false;
            }
        }


        return check;
    }

    /**
     *
     * @param data the form data value to verify against the rule
     * @return whether the form data value matches the rule, displays errors in the console if it isn't
     */
    public void checkDataRules(String data) throws RuleException {

        // TODO: check excluded field in sent form
        // check non-optional field being absent
        if (!optional && (data == null || data.equals(""))) {
            throw new RuleException("Non-optional field wasn't filled out");
        }

        // check matching type (data is assumed a string)
        switch (fieldType) {
            case INTEGER:
                int val = Integer.parseInt(data);
                break;
            case STRING:
                break;
            case FLOAT:
                float fl = Float.parseFloat(data);
                break;
            case DATE:
                LocalDate date = LocalDate.parse(data);
                break;
            case DATETIME:
                LocalDateTime datetime = LocalDateTime.parse(data);
                break;
            case BOOLEAN:
                boolean bool = Boolean.parseBoolean(data);
                if (!"true".equals(data) && !"false".equals(data))
                    throw new RuleException("Data not a boolean");
                break;
            case SELECTOR:
                // split response string?
                break;

        }

        for (TypeRule rule : typeRules) {
            try {
                rule.verifyFormData(data, fieldType);
            }catch (TypeRuleFormDataException e) {
                throw new RuleException("Rule not respected: Data" + data + "does not match rule" + rule.inspect() + ". Exception : " + e.getMessage());
            }
        }
    }

    public void setSelectorValues(ArrayList<String> selectorValues) {
        if (this.fieldType == null) {
            this.fieldType = FieldType.SELECTOR;
        }
        if (this.fieldType == FieldType.SELECTOR) {
            this.selectorValues = selectorValues;
        }
    }

    /*
    public void setSelectorValues(String[] selectorValues) {
        setSelectorValues(new ArrayList<>(List.of(selectorValues)));
    }
    */
}