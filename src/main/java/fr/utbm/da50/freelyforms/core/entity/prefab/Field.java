package fr.utbm.da50.freelyforms.core.entity.prefab;

import org.springframework.data.annotation.PersistenceCreator;

/**
 * The Field class represents the graphical fields present on the form.
 * It has a Rule attribute which validity it may verify.
 * The Field class may also verify that a given form data value is valid against its field using the Rule class.
 * Verification of valid form data values is done twice, in the Frontend app and in the backend
 * This is to catch invalid API form submissions as well as to check for duplicates.
 *
 * @author Pourriture
 */
public class Field {

    /** Name of the field. Should be unique within a group.
     */
    private String name;
    /** Field display label on the frontend
     * */
    private String label;

    /** Hint text within the field
     * */
    private String caption;

    /**
     * Rules are used by the field to verify that data entered inside a field are valid
     */
    Rule rules;



    @PersistenceCreator
    public Field(String name, String label, String caption, Rule rules) {
        this.name = name;
        this.label = label;
        this.caption = caption;
        this.rules = rules;
    }

    // count value for the test constructor
    static int count = 0;

    // Test constructor
    Field() {
        this.name="fieldName" + count;
        this.label = "fieldLabel";
        this.caption = "fieldCaption";
        this.rules = new Rule();
        count++;
    }


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Rule getRules() {
        return rules;
    }

    public void setRules(Rule rules) {
        this.rules = rules;
    }
}
