package com.engeto.ja;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate lastWatering;
    private int frequencyOfWatering;

    public void setPlant(String name, String notes, LocalDate planted, LocalDate lastWatering, int frequencyOfWatering) {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.lastWatering = lastWatering;
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public void setPlant() {
        this.notes = "";
        this.lastWatering = LocalDate.now();
    }

    public void setPlant(String name) {
        this.name = name;
        this.notes = "";
        this.planted = LocalDate.now();
        this.lastWatering = LocalDate.now();
        this.frequencyOfWatering = 7;
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

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public String getWateringInfo() {
        return name + ", last watered " + lastWatering.format(DateTimeFormatter.ofPattern("d.M.y")) + ", next recommended watering "
                + lastWatering.plusDays(frequencyOfWatering).format(DateTimeFormatter.ofPattern("d.M.y")) + ".";
    }
}
