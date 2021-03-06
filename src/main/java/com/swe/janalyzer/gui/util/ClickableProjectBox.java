package com.swe.janalyzer.gui.util;

import com.swe.janalyzer.data.metriken.Project;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ClickableProjectBox extends HBox {

    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm 'Uhr' dd.MM.yy");

    private Project data;
    private Path storagePath;
    private CheckBox checkBox;

    private String formatDate(Date date){
        return formatter.format(date);
    }

    public ClickableProjectBox(Project project,
                               Path storagePath,
                               ChangeListener<Boolean> onCheckBoxValueChanged,
                               EventHandler<MouseEvent> onBoxClicked) {
        data = project;
        this.storagePath = storagePath;

        checkBox = new CheckBox();
        checkBox.selectedProperty().addListener(onCheckBoxValueChanged);

        VBox labelContainer = new VBox();
        final Label projName = new Label(project.getName());
        final Label timeOfAnalysis = new Label(formatter.format(project.getTimeOfAnalysis()));
        labelContainer.getChildren().addAll(projName, timeOfAnalysis);

        this.getChildren().addAll(checkBox, labelContainer);

        this.setOnMouseClicked(onBoxClicked);
    }

    public Project getData() {
        return data;
    }

    public boolean isSelected(){
        return checkBox.isSelected();
    }

    public boolean removeStorageFile() {
        try {
            Files.delete(storagePath);
            return true;
        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Fehler");
            a.setHeaderText("Fehler beim Löschen");
            a.setContentText("Das Projekt " + data.getName() + " konnte nicht gelöscht werden.\n" +
                    "Soll das Projekt trotzdem aus der Historie entfernt werden?");
            a.getButtonTypes().clear();
            a.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = a.showAndWait();
            if(!buttonType.isPresent()) {
                return false;
            }else{
                return buttonType.get().equals(ButtonType.YES);
            }
        }
    }
}
