package fr.utbm.da50.freelyforms.core.entity.prefab;

import fr.utbm.da50.freelyforms.core.entity.prefab.rule.TypeRule;
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
    private boolean optional;
    /**
     * Which groups and fields (in the group or group.field format) this rule excludes from being filled in if its field has a value
     */
    @Field
    private ArrayList<String> excludes;
    /**
     * Rules defining  verifications depending on fieldType and the specific class inherited from abstract class TypeRule in the array
     */
    @Field
    private ArrayList<TypeRule> typeRules;
    /**
     * Whether the field will be hidden on the front-end app
     */
    @Field
    boolean hidden;
    /**
     * Possible list of selector values, such as a dropdown or a list selectable with a radio-button-style array (only for use with the SELECTOR fieldType)
     */
    @Field
    private ArrayList<String> selectorValues;
    /**
     * Data type for the field value
     */
    @Field
    private FieldType fieldType;

    // Test constructor
    Rule() {
        this.optional = true;
        this.excludes = new ArrayList<>();
        this.typeRules = new ArrayList<>();
        this.fieldType = FieldType.INTEGER;
        this.hidden = false;
        this.selectorValues = null;
    }

    Rule(FieldType fieldType, boolean optional, boolean hidden, ArrayList<String> excludes) {
        this.fieldType = fieldType;
        this.optional = optional;
        this.hidden = hidden;
        this.excludes = excludes;
        this.selectorValues = new ArrayList<>();
        this.typeRules = new ArrayList<>();

    }
    Rule(FieldType fieldType, boolean optional, boolean hidden, String[] excludes) {
        this.fieldType = fieldType;
        this.optional = optional;
        this.hidden = hidden;
        this.excludes = new ArrayList<>(List.of(excludes));
        this.selectorValues = new ArrayList<>();
        this.typeRules = new ArrayList<>();

    }

    Rule(FieldType fieldType, boolean optional, boolean hidden, ArrayList<String> excludes, ArrayList<String> selectorValues) {
        if (fieldType == FieldType.SELECTOR) {
            this.selectorValues = selectorValues;
        }else {
            this.selectorValues = new ArrayList<>();
        }
        this.fieldType = fieldType;
        this.optional = optional;
        this.hidden = hidden;
        this.excludes = excludes;
        this.typeRules = new ArrayList<>();

    }
    Rule(FieldType fieldType, boolean optional, boolean hidden, String[] excludes, String[] selectorValues) {
        if (fieldType == FieldType.SELECTOR) {
            this.selectorValues = new ArrayList<>(List.of(selectorValues));
        }else {
            this.selectorValues = new ArrayList<>();
        }
        this.fieldType = fieldType;
        this.optional = optional;
        this.hidden = hidden;
        this.excludes = new ArrayList<>(List.of(excludes));
        this.typeRules = new ArrayList<>();

    }



    @PersistenceCreator
    Rule(FieldType fieldType, boolean optional, boolean hidden, ArrayList<String> excludes, ArrayList<String> selectorValues, ArrayList<TypeRule> typeRules) {
        if (fieldType == FieldType.SELECTOR) {
            this.selectorValues = selectorValues;
        }else {
            this.selectorValues = new ArrayList<>();
        }
        this.fieldType = fieldType;
        this.optional = optional;
        this.hidden = hidden;
        this.excludes = excludes;
        this.typeRules = typeRules;

    }


    // Test constructor
    public Rule(FieldType fieldType) {
        this.fieldType = fieldType;
        this.optional = false;
        this.excludes = new ArrayList<>();
        this.hidden = false;
        this.selectorValues = new ArrayList<>();
        this.typeRules = new ArrayList<>();

    }

    Rule(String[] selectorValues) {
        this.fieldType = FieldType.SELECTOR;
        this.selectorValues = new ArrayList<>(List.of(selectorValues));
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
    public Boolean checkDataRules(String data) {

        // TODO: check excluded field in sent form


        try {
            // check non-optional field being absent
            if (!optional && (data == null || data.equals(""))) {
                throw new Exception("Non-optional field wasn't filled out");
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
                        throw new Exception("Data not a boolean");
                    break;
                case SELECTOR:
                    // split response string?
                    break;

            }

            for (TypeRule rule : typeRules) {
                if (!rule.verifyFormData(data, fieldType)) {
                    throw new Exception("Rule not respected: Data" + data + "does not match rule" + rule.inspect());
                }
            }

        }
        catch (Exception e) {
            System.out.println("Field rule broken");
            System.out.println(e);
            return false;
        }



        return true;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public ArrayList<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(ArrayList<String> excludes) {
        this.excludes = excludes;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public ArrayList<TypeRule> getTypeRules() {
        return typeRules;
    }

    public void setTypeRules(ArrayList<TypeRule> typeRules) {
        for (TypeRule rule : typeRules) {
            if (!rule.verifyTypeRuleValidity()){
                System.out.println("setTypeRules: Tried to set invalid rule " + rule.inspect());
                return;
            }
            this.typeRules = typeRules;
        }
    }

    /*public void setTypeRules(TypeRule[] typeRules) {
        setTypeRules(new ArrayList<>(List.of(typeRules)));
    }*/

    public ArrayList<String> getSelectorValues() {
        return this.selectorValues;
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