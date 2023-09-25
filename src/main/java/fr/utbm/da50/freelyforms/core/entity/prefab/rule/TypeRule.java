package fr.utbm.da50.freelyforms.core.entity.prefab.rule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.ArrayList;



/**
 * The TypeRule class is an abstract class designed to be inherited by classes that will have methods and values that
 * may be used to verify form data value validity.
 *
 * Adding a new TypeRule requires defining three methods:
 * - an all-attribute constructor tagged with @PersistenceCreator that calls super();,
 *   add valid types for the rule into the associatedTypes array and defines the name attribute
 *   -> import static fr.utbm.da50.freelyforms.core.entity.prefab.Rule.FieldType.*; for the enum of currently-supported field types
 * - a verifyTypeRuleValidity() method that determines whether the value attribute matches the new class and its
 *   functionality (e.g. a rule defining the required length of a string should be invalid if the value attribute is a
 *   string instead of an integer)
 * - a verifyFormData() method that determines whether its parameter fulfills the condition of the rule (e.g a rule
 *   defining the required length of a string returns false if the string has a different length)
 *
 * @author Pourriture
 */

// this is used by jackson to determine in which inherited class JSON objects sent to the API should be translated into
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes({
        // It might be a good idea to turn these static string values into enum values like with
        @JsonSubTypes.Type(value = AlternativeDisplay.class, name = "AlternativeDisplay"),
        @JsonSubTypes.Type(value = MaximumRule.class, name = "MaximumRule"),
        @JsonSubTypes.Type(value = MinimumRule.class, name = "MinimumRule"),
        @JsonSubTypes.Type(value = RegexMatch.class, name = "RegexMatch"),
        @JsonSubTypes.Type(value = SelectDataSet.class, name = "SelectDataSet")
})
public abstract class TypeRule {

    // todo: do not save this in mongodb database as it is added by the constructor every time
    ArrayList<Rule.FieldType> associatedTypes;

    /**
     * value of the rule, to be checked against field data
     */
    @Field
    private String value;
    /**
     * name attribute used by Jackson to determine TypeRule inherited class (more testing may be necessary)
     */
    @Field
    private String name;



    public TypeRule(Object value) {
        this.setValue(value);
        this.name = "TypeRule";
        this.associatedTypes = new ArrayList<>();
    }


    public TypeRule(String value, String name, ArrayList<Rule.FieldType> associatedTypes) {
        this.value = value;
        this.name = name;
        this.associatedTypes = associatedTypes;
    }

    /**
     * @return true if the value of the rule is correctly formatted
     */
    abstract public boolean verifyTypeRuleValidity();


    /**
     * @param data entered form data to validate
     * @param fieldType the actual type of the field
     * @return true if data/fieldtype are valid for this rule
     */
    abstract public boolean verifyFormData(String data, Rule.FieldType fieldType);

    /**
     * @param fieldType the actual type of the field
     * @return true if the field matches a valid type for the rule
     */
    public boolean matchesFieldTypes (Rule.FieldType fieldType) {
        if (associatedTypes.contains(fieldType)) {
            return true;
        }
        return false;
    }

    public String inspect() {
        return this.name + " : " + this.value;
    }

    public String getValue() {
        return value;
    }

    /**
     * @param val a var of a primitive type that can be made into a string
     */
    public void setValue(Object val) {
        this.value = String.valueOf(val);
    }



    public ArrayList<Rule.FieldType> getAssociatedTypes() {
        return associatedTypes;
    }

    public void setAssociatedTypes(ArrayList<Rule.FieldType> associatedTypes) {
        this.associatedTypes = associatedTypes;
    }

    public void setValue(String val) {
        this.value = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}