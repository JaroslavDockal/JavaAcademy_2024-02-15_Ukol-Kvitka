import com.engeto.ja.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileNameIn = Settings.getFileNameIn();
        String fileNameOut = Settings.getFileNameOut();

        PlantCollection plantCollection = new PlantCollection();

        printDivider();
        loadPlants(plantCollection, fileNameIn);
        printDivider();

        printPlants(plantCollection);
        printDivider();

        //Zvolil jsem tuhle cestu proto aby se vyjímky při vytváření new Plant odchytávali v rovnou v metodě níže a nebylo třeba je řešit tu...
        addNewPlant(plantCollection, "Kaktus", "Pozor na trny",96,LocalDate.of(2021, 6, 12), LocalDate.of(2021, 6, 1));
        addNewPlant(plantCollection, "Fikus");
        printDivider();

        printPlant(plantCollection, 1);
        printDivider();

        removePlant( plantCollection, 1);
        printDivider();

        printPlant(plantCollection, 1);
        printDivider();

        updateLastWatering(plantCollection, 0);
        printPlantsSortedByWatering(plantCollection.getPlants());
        printDivider();

        savePlants(plantCollection, fileNameOut);
        loadPlants(plantCollection, fileNameOut);
        printDivider();

        printPlantsSortedByName(plantCollection.getPlants());

        //testExceptions();

    }

    private static void loadPlants(PlantCollection plantCollection, String fileName){
        System.out.println("Načítáme seznam rostlin ze souboru: "+ fileName);
        try {
            plantCollection.loadPlantsFromFile(fileName);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při načítání květin:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }
    }

    private static void savePlants(PlantCollection plantCollection, String fileName) {
        System.out.println("Zapisujeme do souboru: "+ fileName);
        try {
            plantCollection.savePlantsToFile(fileName);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při ukládání květin:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }
    }

    private static void printPlants(PlantCollection plantCollection) {
        if (plantCollection.getPlants().isEmpty()) {
            System.out.println("Seznam květin je prázdný.");
        } else {
            System.out.println("Seznam květin:\t" + plantCollection.getPlants());
        }
    }

    private static void addNewPlant(PlantCollection plantCollection, String name, String notes, int wateringFrequency, LocalDate lastWatering, LocalDate planted){
        try {
            plantCollection.addPlant(new Plant(name, notes, wateringFrequency, lastWatering, planted));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny \"" + name + "\":\n" + e.getLocalizedMessage());
        }
    }

    private static void addNewPlant(PlantCollection plantCollection, String name, int wateringFrequency, LocalDate planted){
        try {
            plantCollection.addPlant(new Plant(name, wateringFrequency, planted));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny \"" + name + "\":\n" + e.getLocalizedMessage());
        }
    }

    private static void addNewPlant(PlantCollection plantCollection, String name){
        try {
            plantCollection.addPlant(new Plant(name));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny \"" + name + "\":\n" + e.getLocalizedMessage());
        }
    }

    private static void removePlant(PlantCollection plantCollection, int index){
        try {
            plantCollection.removePlant(index);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při odebírání květiny na indexu " + index + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void printPlant(PlantCollection plantCollection, int index) {
        try {
            System.out.println("Rostlina na indexu " + index + ": " + plantCollection.getPlant(index));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při získávání květiny na indexu " + index + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void updateLastWatering(PlantCollection plantCollection, int index, LocalDate lastWatering) {
        try {
            plantCollection.updateLastWatering(index, lastWatering);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při aktualizaci data poslední zálivky:\n" + e.getLocalizedMessage());
        }
    }

    private static void updateLastWatering(PlantCollection plantCollection, int index) {
        try {
            plantCollection.updateLastWatering(index);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při aktualizaci data poslední zálivky:\n" + e.getLocalizedMessage());
        }
    }

    private static void printPlantsSortedByWatering(List<Plant> plants) {
        if (plants.isEmpty()) {
            System.out.println("Seznam rostlin je prázdný.");
        } else {
            sortPlants(plants, PlantSortingCriteria.LAST_WATERING);
            System.out.println("Přehled květin řazený dle data poslední zálivky:");
            for (Plant plant : plants) {
                System.out.println(plant.getWateringInfo());
            }
        }
    }

    private static void printPlantsSortedByName(List<Plant> plants) {
        if (plants.isEmpty()) {
            System.out.println("Seznam rostlin je prázdný.");
        } else {
            sortPlants(plants, PlantSortingCriteria.NAME);
            System.out.println("Seznam květin řazený podle názvu:" + plants);
        }
    }

    private static void sortPlants(List<Plant> plants, PlantSortingCriteria sortingCriteria) {
        try {
            Collections.sort(plants, (plant1, plant2) -> plant1.compareTo(plant2, sortingCriteria));
        } catch (IllegalArgumentException e) {
            System.err.println("Nepodporované kritérium řazení: " + sortingCriteria);
        }
    }

    private static void printDivider() {
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void testExceptions() {
        PlantCollection plantCollectionTest = new PlantCollection();

        //Question: Jsou chyby vždycky až na konci, nebo to jde vypsat "tak jak to jede"?

        printDivider();printDivider();

        loadPlants(plantCollectionTest, "resources/neexistujiciSoubor.txt");
        loadPlants(plantCollectionTest, "resources/kvetiny-spatny-pocet-prvku.txt");
        loadPlants(plantCollectionTest, "resources/kvetiny-spatne-datum.txt");
        loadPlants(plantCollectionTest, "resources/kvetiny-spatne-frekvence.txt");

        //Špatně zadaný index
        addNewPlant(plantCollectionTest, "Orchidej", -7,  LocalDate.of(2021, 6, 12));
        //Špatné datum poslední zálivky
        addNewPlant(plantCollectionTest, "Růže", "" ,7,  LocalDate.of(2021, 6, 12), LocalDate.of(2021, 7, 1));
        //Zadaný neexistující index
        removePlant(plantCollectionTest, 9);
        //Špatně zadaný index
        printPlant(plantCollectionTest, -11);
        //Zadaný neexistující index
        updateLastWatering(plantCollectionTest, 2);
        //Špatné datum poslední zálivky
        updateLastWatering(plantCollectionTest, 1, LocalDate.of(2000, 6, 12));
        // Nepodporované kritérium řazení
        sortPlants(plantCollectionTest.getPlants(), PlantSortingCriteria.TESTING);

        savePlants(plantCollectionTest, "resources/souborJenProCteni.txt");

    }

}