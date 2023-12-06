package fr.utbm.da50.freelyforms.core.service;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.entity.prefab.Field;
import fr.utbm.da50.freelyforms.core.entity.prefab.Group;
import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator service to automatically generate basic data that may be then added to the database.
 * It is designed to generate data for demos and testing.
 * This service is incomplete and its generated FormData objects may not match the TypeRules of the prefabs.
 * This service does not write data to the database by itself.
 *
 *
 * TODO : Use this service to populate your database!
 * Add the Street prefab and the Person prefab to the database (be careful to only add them once each)
 * Then call generateFormData and pass each prefab as an argument to generate sample data that you can save to the database as well
 */
public class Generator {

    /**
     * 
     * @return the Street prefab
     */
    public static Prefab generateStreet() {
        ArrayList<Field> streetFields = new ArrayList<>();
        streetFields.add(new Field("number", "Numéro", "16",new Rule(Rule.FieldType.INTEGER)));
        streetFields.get(0).getRules().addTypeRule(new MinimumRule(0));
        streetFields.add(new Field("streetName", "Rue", "Allée des maraîchers", new Rule(Rule.FieldType.STRING)));
        streetFields.get(1).getRules().addTypeRule(new MaximumRule(30));
        ArrayList<Field> cityFields = new ArrayList<>();
        cityFields.add(new Field("cityName","Ville","Aubervilliers", new Rule(Rule.FieldType.STRING)));
        cityFields.get(0).getRules().addTypeRule(new MinimumRule(3));
        cityFields.add(new Field("country", "Pays","", new Rule(Rule.FieldType.SELECTOR)));
        String[] countries = new String[]{"France", "Belgique", "Suisse"};
        cityFields.get(1).getRules().setSelectorValues(new ArrayList<>(List.of(countries)));
        cityFields.get(1).getRules().addTypeRule(new AlternativeDisplay("DROPDOWN"));
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(Group.builder().name("streetFields").label("Rue").caption("Données de rue").fields(streetFields).build());
        groups.add(Group.builder().name("cityFields").label("Ville").caption("Données de ville").fields(streetFields).build());
        return new Prefab("streets", "Rues","", groups);
    }

    /**
     * 
     * @return the Person prefab
     */
    public static Prefab generatePerson() {
        ArrayList<Field> personFields = new ArrayList<>();
        TypeRule nameRule = new MaximumRule(50);
        List<TypeRule> typeRules = new ArrayList<>();
        typeRules.add(nameRule);

        TypeRule sexRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.CHECKBOX);
        List<TypeRule> sexTypeRules = new ArrayList<>();
        sexTypeRules.add(sexRule);
        List<String> sexSelectorValues = new ArrayList<>();
        sexSelectorValues.add("Homme");
        sexSelectorValues.add("Femme");

        personFields.add(new Field("name","Nom","",Rule.builder().fieldType(Rule.FieldType.STRING).typeRules(typeRules).build()));
        personFields.add(new Field("dob","Date de naissance", "", new Rule(Rule.FieldType.DATE)));
        personFields.add(new Field("street","Rue de domicile","",new Rule(Rule.FieldType.SELECTOR)));
        personFields.get(2).getRules().addTypeRule(new SelectDataSet("streets.streetFields.streetName"));
        personFields.add(new Field("phone","Téléphone","0612345678", new Rule(Rule.FieldType.STRING)));
        personFields.get(3).getRules().addTypeRule(new RegexMatch("(('+'[0-9]{2})|0)[0-9]{8}"));
        personFields.add(Field.builder().name("sex").label("Sexe").rules(Rule.builder().fieldType(Rule.FieldType.STRING).typeRules(sexTypeRules).selectorValues(sexSelectorValues).build()).build());


        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("personFields", "Identité", "", personFields));

        return new Prefab("people", "Gens", "Personnes", groups);

    }

    /**
     * @param prefab to generate form data for
     * @return random form data matching the Rule classes of the prefab (not the TypeRule classes yet!)
     */
    public static FormData generateFormData(Prefab prefab) {
        FormData ret = new FormData(prefab.getName(), new ArrayList<>());

        Random rand = new Random();
        String[] stringList = {"Value", "Tesco", "Pinkie", "Foragers", "HostelStreet", "Mans"};


        for (Group g : prefab.getGroups()) {
            ret.addGroup(g.getName());
            for (Field f : g.getFields()) {
                String value = "";
                switch (f.getRules().getFieldType()) {
                    case SELECTOR -> {
                        if (f.getRules().getSelectorValues().size() == 0) {
                            value = "ParisAvenue";
                        } else {
                            value = f.getRules().getSelectorValues().get(rand.nextInt(f.getRules().getSelectorValues().size()));
                        }
                    }
                    case STRING-> value = stringList[rand.nextInt(stringList.length) ];
                    case INTEGER-> value = String.valueOf(rand.nextInt(100));
                    case FLOAT-> value = String.valueOf(rand.nextFloat());
                    case DATE-> value = "2004/01/31";
                    case DATETIME-> value = "2004/01/31 18:35:23";
                    case BOOLEAN-> value = String.valueOf(rand.nextBoolean());
                }
                try {
                    ret.getGroup(g.getName()).addField(f.getName(), value);
                } catch (Exception ignored) {}

            }
        }

        return ret;
    }


}
