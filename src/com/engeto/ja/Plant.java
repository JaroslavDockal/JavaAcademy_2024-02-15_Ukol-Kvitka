package com.engeto.ja;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate lastWatering;
    private int wateringFrequency;

    public Plant(String name, String notes, int wateringFrequency, LocalDate lastWatering, LocalDate planted) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        setLastWatering(lastWatering);
        setWateringFrequency(wateringFrequency);
    }

    // Nepoužito, ale požadováno v zadání
    public Plant() throws PlantException {
        this.notes = "";
        setLastWatering(LocalDate.now());
    }

    public Plant(String name) throws PlantException {
        this(name, "", 7, LocalDate.now(), LocalDate.now());
    }

    private void setWateringFrequency(int wateringFrequency) throws PlantException {
        if (wateringFrequency < 1) {
            throw new PlantException("Zadaná špatná četnost zálivky. Zadaná četnost: " + wateringFrequency + " dní.");
        }
        this.wateringFrequency = wateringFrequency;
    }

    // ToDo Jak jinak to udělat? Dá se použít ten konstruktor na update, nebo jen na vytvoření nové rostliny?
    public void setLastWatering(LocalDate lastWatering) throws PlantException {
        if (lastWatering.isBefore(planted)) {
            throw new PlantException("Datum poslední zálivky nesmí být dřív než zasazení. Zadané datum: " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) + ".");
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
                ", další doporučená zálivka " + lastWatering.plusDays(wateringFrequency).format(DateTimeFormatter.ofPattern("d.M.y")) + ".\n";
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
        return name + ", zasazeno " + planted.format(DateTimeFormatter.ofPattern("d.M.y")) +
                ", naposledy zalito " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) +
                ", frekvence zálivky " + wateringFrequency + " " + dayString + ".";
    }

}
