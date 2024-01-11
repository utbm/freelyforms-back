package fr.utbm.da50.freelyforms.core.service;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.entity.prefab.Field;
import fr.utbm.da50.freelyforms.core.entity.prefab.Group;
import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.*;

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
     *
     * @return the Person prefab
     */
    public static Prefab generateCars() {
        List<Field> generalInformationGroup = new ArrayList<>();
        List<Field> technicalInformationGroup = new ArrayList<>();
        List<Field> feedbackInformationGroup = new ArrayList<>();

        TypeRule sexRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.CHECKBOX);
        List<TypeRule> sexTypeRules = new ArrayList<>();
        sexTypeRules.add(sexRule);

        TypeRule yearRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.CALENDAR);
        List<TypeRule> yearRules = new ArrayList<>();
        yearRules.add(yearRule);

        TypeRule namesMaximumRule = new MaximumRule(50);
        List<TypeRule> namesRules = new ArrayList();
        namesRules.add(namesMaximumRule);

        Field brand = Field.builder().name("brand").label("Quel est la marque de la voiture?").caption("Citroen").rules(Rule.builder().fieldType(Rule.FieldType.STRING).typeRules(namesRules).build()).build();
        Field model = Field.builder().name("model").label("Quel est le modèle de la voiture?").caption("Xsara").rules(Rule.builder().fieldType(Rule.FieldType.STRING).typeRules(namesRules).build()).build();
        Field year = Field.builder().name("year").label("Quelle est la date de mise en circulation de la voiture?").caption("12/06/2000").rules(Rule.builder().fieldType(Rule.FieldType.DATE).typeRules(yearRules).build()).build();

        generalInformationGroup.add(brand);
        generalInformationGroup.add(model);
        generalInformationGroup.add(year);

        TypeRule engineTypeRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.DROPDOWN);
        List<TypeRule> engineRules = new ArrayList();
        engineRules.add(engineTypeRule);

        List<String> engineSelectorValues = new ArrayList<>();
        engineSelectorValues.add("1.8");
        engineSelectorValues.add("1.9");
        engineSelectorValues.add("2.0");
        engineSelectorValues.add("2.0 HDI");

        TypeRule optionsTypeRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.MULTIPLE_CHOICE);
        List<TypeRule> optionsRules = new ArrayList();
        optionsRules.add(optionsTypeRule);

        List<String> optionsSelectorValues = new ArrayList<>();
        optionsSelectorValues.add("Sièges chauffants");
        optionsSelectorValues.add("Toit ouvrant");
        optionsSelectorValues.add("Caméra de recul");
        optionsSelectorValues.add("Stationnement automatique");
        optionsSelectorValues.add("Éclairage directionnel");
        optionsSelectorValues.add("Suivi de ligne");

        TypeRule colorTypeRule = new AlternativeDisplay(AlternativeDisplay.AlternativeDisplays.RADIO);
        List<TypeRule> colorTypeRules = new ArrayList();
        colorTypeRules.add(colorTypeRule);

        List<String> colorSelectorValues = new ArrayList<>();
        colorSelectorValues.add("Noir");
        colorSelectorValues.add("Blanc");
        colorSelectorValues.add("Rouge");
        colorSelectorValues.add("Bleu");



        Field engine = Field.builder().name("engine").label("Quel est la motorisation de la voiture?").caption("2.0 HDI").rules(Rule.builder().fieldType(Rule.FieldType.SELECTOR).selectorValues(engineSelectorValues).typeRules(engineRules).build()).build();
        Field options = Field.builder().name("options").label("Quelles sont les options de la voiture?").rules(Rule.builder().fieldType(Rule.FieldType.SELECTOR).selectorValues(optionsSelectorValues).typeRules(optionsRules).build()).build();
        Field color = Field.builder().name("color").label("Quelle est la couleur de la voiture?").rules(Rule.builder().fieldType(Rule.FieldType.SELECTOR).selectorValues(colorSelectorValues).typeRules(colorTypeRules).build()).build();

        technicalInformationGroup.add(engine);
        technicalInformationGroup.add(options);
        technicalInformationGroup.add(color);

        TypeRule emailTypeRule = new EmailRegexMatch();
        List<TypeRule> emailTypeRules = new ArrayList<>();
        emailTypeRules.add(emailTypeRule);

        Field comment = Field.builder().name("comment").label("Quel commentaire pouvez vous faire sur cette voiture?").caption("La Xsara elle est aberrante frérot.").rules(Rule.builder().fieldType(Rule.FieldType.STRING).build()).build();
        Field tripsNumber = Field.builder().name("tripsNumber").label("Combien de trajets (en moyenne) faites vous par semaine avec cette voiture?").caption("12").rules(Rule.builder().fieldType(Rule.FieldType.INTEGER).build()).build();
        Field email = Field.builder().name("email").label("Entrez votre adresse email pour tenter de remporter la magnifique Xsara RS").caption("gmk@miteux.fr").rules(Rule.builder().fieldType(Rule.FieldType.STRING).typeRules(emailTypeRules).build()).build();


        feedbackInformationGroup.add(comment);
        feedbackInformationGroup.add(tripsNumber);
        feedbackInformationGroup.add(email);

        ArrayList<Group> groups = new ArrayList<>();
        Group generalInformation =  Group.builder().name("generalInformation").label("Informations générales sur la voiture").fields(generalInformationGroup).build();
        Group technicalInformation = Group.builder().name("technicalInformation").label("Informations techniques sur la voiture").fields(technicalInformationGroup).build();
        Group feedbackInformation = Group.builder().name("feedbackInformation").label("Feeback de l'utilisateur sur la voiture").fields(feedbackInformationGroup).build();
        groups.add(generalInformation);
        groups.add(technicalInformation);
        groups.add(feedbackInformation);

        return Prefab.builder().name("cars").label("Formulaire sur les voitures").groups(groups).build();

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
