package com.engeto.ja;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant implements Comparable<Plant> {
    private final String name;
    private final String notes;
    private final LocalDate planted;
    private LocalDate lastWatering;
    private int wateringFrequency;

    public Plant(String name, String notes, int wateringFrequency, LocalDate lastWatering, LocalDate planted) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        setLastWatering(lastWatering);
        setWateringFrequency(wateringFrequency);
    }

    public Plant(String name, int wateringFrequency, LocalDate planted) throws PlantException {
        this(name, "", wateringFrequency, LocalDate.now(), planted);
    }

    public Plant(String name) throws PlantException {
        this(name, 7, LocalDate.now());
    }

    private void setWateringFrequency(int wateringFrequency) throws PlantException {
        if (wateringFrequency < 1) {
            throw new PlantException("Zadaná špatná četnost zálivky. Zadaná četnost: " + wateringFrequency + " dní.");
        }
        this.wateringFrequency = wateringFrequency;
    }

    public void setLastWatering(LocalDate lastWatering) throws PlantException {
        if (lastWatering.isBefore(planted)) {
            throw new PlantException("Datum poslední zálivky nesmí být dřív než datum zasazení. Zadané datum: " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) + ".");
        }
        this.lastWatering = lastWatering;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public LocalDate getLastWatering() {
        return lastWatering;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public String getWateringInfo() {
        return name + ", poslední zálivka " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) +
                ", další doporučená zálivka " + lastWatering.plusDays(wateringFrequency).format(DateTimeFormatter.ofPattern("d.M.y")) + ".";
    }

    @Override
    public String toString() {
        String dayString;
        if (wateringFrequency == 1)  {
            dayString = "den";
        } else if (wateringFrequency < 5) {
            dayString = "dny";
        } else {
            dayString = "dní";
        }
        return "\n" + name + ", zasazeno " + planted.format(DateTimeFormatter.ofPattern("d.M.y")) +
                ", naposledy zalito " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) +
                ", frekvence zálivky " + wateringFrequency + " " + dayString + ".";
    }

    @Override
    public int compareTo(Plant otherPlant) {
        return compareTo(otherPlant, PlantSortingCriteria.NAME);
    }

    public int compareTo(Plant otherPlant, PlantSortingCriteria sortingCriteria) {
        switch (sortingCriteria) {
            case NAME:
                return this.name.compareTo(otherPlant.name);
            case LAST_WATERING:
                return this.lastWatering.compareTo(otherPlant.lastWatering);
            default:
                throw new IllegalArgumentException("Nepodporované kritérium řazení: " + sortingCriteria);
        }
    }
}
