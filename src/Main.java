import com.engeto.ja.Plant;
import com.engeto.ja.PlantCollection;
import com.engeto.ja.PlantException;
import com.engeto.ja.Settings;

import java.time.LocalDate;

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
        printPlantsSortedByWatering(plantCollection);
        printDivider();

        savePlants(plantCollection, fileNameOut);
        loadPlants(plantCollection, fileNameOut);
        printDivider();

        printPlantsSortedByName(plantCollection);

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
            System.err.println("Nastala chyba při přidávání květiny " + name + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void addNewPlant(PlantCollection plantCollection, String name, int wateringFrequency, LocalDate planted){
        try {
            plantCollection.addPlant(new Plant(name, wateringFrequency, planted));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny " + name + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void addNewPlant(PlantCollection plantCollection, String name){
        try {
            plantCollection.addPlant(new Plant(name));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny " + name + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void removePlant(PlantCollection plantCollection, int index){
        try {
            plantCollection.removePlant(index);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při odebírání květin:\n" + e.getLocalizedMessage());
        }
    }

    private static void printPlant(PlantCollection plantCollection, int index) {
        try {
            System.out.println("Rostlina na indexu " + index + ": " + plantCollection.getPlant(index));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při získávání květiny na indexu " + index + ":\n" + e.getLocalizedMessage());
        }
    }

    private static void updateLastWatering(PlantCollection plantCollection, int index) {
        try {
            plantCollection.updateLastWatering(index);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při aktualizaci data poslední zálivky:\n" + e.getLocalizedMessage());
        }
    }

    private static void printPlantsSortedByWatering(PlantCollection plantCollection) {
        if (!plantCollection.getPlants().isEmpty()) {
            System.out.println("Přehled květin řazený podle data poslední zálivky:\n" + plantCollection.getPlantsSortedByWatering());
        }
    }

    private static void printPlantsSortedByName(PlantCollection plantCollection) {
        if (!plantCollection.getPlants().isEmpty()) {
            System.out.println("Přehled květin řazený dle abecedy:" + plantCollection.getPlantsSortedByName());
        }
    }

    private static void printDivider() {
        System.out.println("--------------------------------------------------------------------------");
    }

}