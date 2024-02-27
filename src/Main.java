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

        System.out.println("Načítáme seznam rostlin ze souboru: "+ Settings.getFileName());
        try {
            plantCollection.loadPlantsFromFile(fileName);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při načítání květin:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }

           if (plantCollection.getPlants().isEmpty()) {
            System.out.println("Seznam květin je prázdný.");
        } else {
            System.out.println("Seznam květin v seznamu:\n" + plantCollection.getPlants());
        }

        // ToDo: Dá se to udělat lépe (tak aby se to nemuselo psát vždycky znovu)?
        try {
            plantCollection.addPlant(new Plant("Kaktus", "Pozor na trny",96,LocalDate.of(2021, 6, 12), LocalDate.of(2021, 6, 1)));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny "  + e.getLocalizedMessage());
        }

        try {
            plantCollection.addPlant(new Plant("Fikus"));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny:\n" + e.getLocalizedMessage());
        }

        try {
            plantCollection.addPlant(new Plant("Orchidej", "Nepřelévat", 7, LocalDate.of(2021, 6, 12), LocalDate.of(2021, 6, 1)));
        } catch (PlantException e) {
            System.err.println("Nastala chyba při přidávání květiny:\n" + e.getLocalizedMessage());
        }

        try {
            plantCollection.removePlant(1);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při odebírání květin:\n" + e.getLocalizedMessage());
        }

        try {
            plantCollection.updateLastWatering(0);
        } catch (PlantException e) {
            System.err.println("Nastala chyba při aktualizaci data poslední zálivky:\n" + e.getLocalizedMessage());
        }

        if (!plantCollection.getPlants().isEmpty()) {
            System.out.println("Přehled zálivek:\n" + plantCollection.getPlantsSortedByWatering());
        }

        System.out.println("Zapisujeme do souboru: "+ fileNameOut);
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