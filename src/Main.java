import com.engeto.ja.Plant;
import com.engeto.ja.PlantCollection;
import com.engeto.ja.PlantException;
import com.engeto.ja.Settings;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws PlantException {
        String fileName = Settings.getFileName();
        //String fileName = Settings.getFileNameTest1();
        //String fileName = Settings.getFileNameTest2();
        String fileNameOut = Settings.getFileName();

        PlantCollection plantCollection = new PlantCollection();

        System.out.println("Načítáme seznam rostlin ze souboru: "+ Settings.getFileNameOut());
        try {
            plantCollection.loadPlantsFromFile(fileName);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při načítání květin:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }

        System.out.println("Seznam květin v seznamu:\n" + plantCollection.getPlants());

        // Přidání nových květin
        plantCollection.addPlant(new Plant("Kaktus", "Pozor na trny",14,LocalDate.of(2021, 6, 12), LocalDate.of(2021, 6, 1)));
        plantCollection.addPlant(new Plant("Fikus"));

        // Odstranění květiny
        plantCollection.removePlant(1);

        // Aktualizované datum poslední zálivky
        plantCollection.updateLastWatering(0);

        System.out.println("Přehled zálivek:\n" + plantCollection.getPlantsSortedByWatering());

        System.out.println("Zapisujeme do souboru: "+ Settings.getFileNameOut());
        try {
            plantCollection.savePlantsToFile(fileNameOut);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při ukládání květin:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }
    }

}