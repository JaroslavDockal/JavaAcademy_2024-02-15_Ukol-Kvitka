package com.engeto.ja;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import static com.engeto.ja.Settings.getDelimiter;

public class PlantCollection {
    private List<Plant> plants = new ArrayList<>();

    public void addPlant(Plant plant) throws PlantException{
        try {
            System.out.println("Přidávám rostlinu: " + plant.toString());
            plants.add(plant);
        } catch (Exception e) {
            throw new PlantException("Nastala chyba při přidávání rostliny:\n" + e.getLocalizedMessage());
        }
    }

    // Method to retrieve a plant by index - not used atm.
    public Plant getPlant(int index) {
        return plants.get(index);
    }

    public void removePlant(int index) throws PlantException{
        if (index >= 0 && index < plants.size()) {
            System.out.println("Odebírám rostlinu: " + plants.get(index).toString());
            plants.remove(index);
        } else {
            throw new PlantException("Nelze odebrat rostlinu na indexu: " + index + " - index je mimo rozsah seznamu.");
        }
    }

    public void updateLastWatering(int index, LocalDate lastWatering) throws PlantException{
        if (index >= 0 && index < plants.size()) {
            try {
                plants.get(index).setLastWatering(lastWatering);
                System.out.println("Poslední zalévání rostliny \"" + plants.get(index).getName() + "\" aktualizováno na: " + plants.get(index).getLastWatering().format(DateTimeFormatter.ofPattern("d.M.y")));
            } catch (PlantException e) {
                throw new PlantException("Chyba při aktualizaci data poslední zálivky pro rostlinu " + plants.get(index).getName() + ":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new PlantException("Nelze aktualizovat datum poslední zálivky pro rostlinu na indexu: " + index + " - index je mimo rozsah seznamu.");
        }
    }

    public void updateLastWatering(int index) throws PlantException{
        updateLastWatering(index, LocalDate.now());
    }

    public void loadPlantsFromFile(String fileName) throws PlantException{
        int lineCounter = 0;
        plants.clear();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine();
                System.out.println(line);
                String[] parts = line.split(getDelimiter());
                if(parts.length != 5) {
                    throw new PlantException("Nesprávný počet položek na řádku číslo: " + lineCounter + ":" + line + "!");
                }
                String name = parts[0];
                String notes = parts[1];
                int wateringFrequency = Integer.parseInt(parts[2]);
                LocalDate lastWatering = LocalDate.parse(parts[3]);
                LocalDate planted = LocalDate.parse(parts[4]);
                Plant plant = new Plant(name, notes, wateringFrequency, lastWatering, planted);
                plants.add(plant);
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        }catch (NumberFormatException e) {
            throw new PlantException("Nesprávný formát čísla na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (DateTimeParseException e) {
            throw new PlantException("Nesprávný formát data na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new PlantException("Nastala chyba při načítání seznamu rostlin ze souboru " + fileName + " (na řádku číslo " + lineCounter + ")!\n" + e.getLocalizedMessage());
        } finally {
            System.out.println(loadFromFileStatusMsg(plants.size()));
        }
    }

    public void savePlantsToFile(String fileName) throws PlantException{
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Plant plant : plants) {
                writer.println(
                        plant.getName() + Settings.getDelimiter() +
                        plant.getNotes() + Settings.getDelimiter() +
                        plant.getWateringFrequency() + Settings.getDelimiter() +
                        plant.getLastWatering() + Settings.getDelimiter() +
                        plant.getPlanted());
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new PlantException("Nastala chyba při zápisu do souboru " + fileName + "!\n" + e.getLocalizedMessage());
        } finally {
            System.out.println(saveToFileStatusMsg(plants.size()));
        }

    }

    // Dalo by se udělat přímo v loadPlantsFromFile, nebo to napsat tak ať není třeba skloňovat. Ale v rámci tréninku... :-)
    private String loadFromFileStatusMsg (int listSize) {
        String loadedStr, itemStr;
        switch (listSize) {
            case 0 -> {
                // Neberu to jako chybu, ale jen jako informaci o stavu
                return "Žádné položky nebyly načteny - soubor je prázdný.";
            }case 1 -> {
                loadedStr = "Načtena ";
                itemStr = " položka.";
            } case 2, 3, 4 -> {
                loadedStr = "Načteny ";
                itemStr = " položky.";
            }
            default -> {
                loadedStr = "Načteno ";
                itemStr = " položek.";
            }
        }
        return loadedStr + listSize + itemStr;
    }

    private String saveToFileStatusMsg (int listSize) {
        String savedStr, itemStr;
        switch (listSize) {
            case 0 -> {
                return "Žádné položky v seznamu - soubor bude prázdný.";
            }case 1 -> {
                savedStr = "Uložena ";
                itemStr = " položka.";
            } case 2, 3, 4 -> {
                savedStr = "Uloženy ";
                itemStr = " položky.";
            }
            default -> {
                savedStr = "Uloženo ";
                itemStr = " položek.";
            }
        }
        return savedStr + listSize + itemStr;
    }

    public List<Plant> getPlants() {
        return new ArrayList<>(plants);
    }

    public StringBuilder getPlantsSortedByWatering() {
        List<Plant> sortedPlants = new ArrayList<>(plants);
        Collections.sort(sortedPlants, Comparator.comparing(Plant::getLastWatering));
        StringBuilder wateringInfo = new StringBuilder();
        for (Plant plant : sortedPlants) {
            wateringInfo.append(plant.getWateringInfo());
        }
        return wateringInfo;
    }

}
