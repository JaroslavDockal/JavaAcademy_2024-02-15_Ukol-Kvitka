/*
Vytvoř třídu pro ukládání seznamu pokojových květin. Jako atribut bude mít kolekci, uchovávající objekty s informacemi o květinách. Chceme mít možnost vytvářet více seznamů a jednotlivé seznamy exportovat do souboru či je ze souboru načítat.
Přidej metody pro přidání nové květiny, získání květiny na zadaném indexu a odebrání květiny ze seznamu.
Přidej do seznamu květin metodu pro načtení květin ze souboru.
    V případě chybného vstupu vyhoď výjimku.
    Do jednoho souboru se vždy uloží obsah jednoho seznamu květin.
Přidej do seznamu květin metodu pro uložení aktualizovaného seznamu do souboru.
 */

package com.engeto.ja;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.engeto.ja.Settings.getDelimiter;

public class PlantCollection {
    private List<Plant> plants = new ArrayList<>();

    public void addPlant(Plant plant) {
        System.out.println("Přidávám rostlinu: " + plant.toString());
        plants.add(plant);
    }

    public Plant getPlant(int index) {
        return plants.get(index);
    }

    public void removePlant(int index) {
        System.out.println("Odebírám rostlinu: " + plants.get(index).toString());
        plants.remove(index);
    }

    public void updateLastWatering(int index, LocalDate lastWatering) {
        try {
            plants.get(index).setLastWatering(lastWatering);
            System.out.println("Poslední zalévání rostliny \"" + plants.get(index).getName() + "\" aktualizováno na: " + plants.get(index).getLastWatering());
        } catch (PlantException e) {
            System.err.println("Chyba při získávání rostliny: " + e.getMessage());
        }
    }

    public void updateLastWatering(int index) {
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
            System.err.println("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        }catch (NumberFormatException e) {
            throw new PlantException("Nesprávný formát čísla na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (DateTimeParseException e) {
            throw new PlantException("Nesprávný formát data na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new PlantException("Nastala chyba při načítání seznamu rostlin ze souboru " + fileName + " (na řádku číslo " + lineCounter + ")!\n" + e.getLocalizedMessage());
        } finally {
            System.out.println("Načteno " + plants.size() + " položek.");
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
            System.out.println("Uloženo " + plants.size() + " položek.");
        }

    }

    public List<Plant> getPlants() {
        return plants;
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
