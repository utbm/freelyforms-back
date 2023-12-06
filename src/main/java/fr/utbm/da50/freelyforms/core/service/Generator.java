package fr.utbm.da50.freelyforms.core.service;


import java.util.ArrayList;
import java.util.List;

import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.entity.formdata.Location;
import fr.utbm.da50.freelyforms.core.entity.formdata.Material;
import fr.utbm.da50.freelyforms.core.entity.prefab.Field;
import fr.utbm.da50.freelyforms.core.entity.prefab.Group;
import fr.utbm.da50.freelyforms.core.entity.prefab.Rule;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.AlternativeDisplay;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.MaximumRule;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.MinimumRule;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.RegexMatch;
import fr.utbm.da50.freelyforms.core.entity.prefab.rule.SelectDataSet;

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

    public static ArrayList<Material> generateMaterials() {
        ArrayList<Material> materialList = new ArrayList<>();
        ArrayList<Location> locationList1 = new ArrayList<>();
        locationList1.add(new Location("Location 1 address",51.52,-0.09,100));
        locationList1.add(new Location("Location 1 address 2",51.53,-0.09,600));
        materialList.add(new Material("Material 1","Type A","Color A",locationList1));
        ArrayList<Location> locationList2 = new ArrayList<>();
        locationList2.add(new Location("Location 2 address",51.52,-0.08,400));
        materialList.add(new Material("Material 2","Type A","Color B",locationList2));
        ArrayList<Location> locationList3 = new ArrayList<>();
        materialList.add(new Material("Material 3","Type B","Color B",locationList3));

        return materialList;
    }


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
        groups.add(new Group("streetFields","Rue","Données de rue",streetFields));
        groups.add(new Group("cityFields","Ville","Données de ville",streetFields));
        return new Prefab("streets", "Rues","", groups);
    }

    /**
     * 
     * @return the Person prefab
     */
    public static Prefab generatePerson() {
        ArrayList<Field> personFields = new ArrayList<>();
        personFields.add(new Field("name","Nom","",new Rule(Rule.FieldType.STRING)));
        personFields.add(new Field("dob","Date de naissance", "", new Rule(Rule.FieldType.DATE)));
        personFields.add(new Field("street","Rue de domicile","",new Rule(Rule.FieldType.SELECTOR)));
        personFields.get(2).getRules().addTypeRule(new SelectDataSet("streets.streetFields.streetName"));
        personFields.add(new Field("phone","Téléphone","0612345678", new Rule(Rule.FieldType.STRING)));
        personFields.get(3).getRules().addTypeRule(new RegexMatch("(('+'[0-9]{2})|0)[0-9]{8}"));

        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("personFields", "Identité", "", personFields));

        return new Prefab("people", "Gens", "Personnes", groups);

    }


}
