package jwave.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import jwave.core.JWave;
import jwave.core.TrackList;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Gui extends Application{
	
	Button button;
	TrackList tracks;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("JWave 1.0");
		Scene scene = new Scene(new VBox(), 960, 480);
		
		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("File");
		
		ListView<String> openJWaveListView = new ListView<String>();
		ObservableList<String> openJWaveListItems = FXCollections.observableArrayList();
		openJWaveListView.setItems(openJWaveListItems);
		openJWaveListView.setPrefHeight(100.0);
		openJWaveListView.setPrefWidth(300.0);
		tracks = new TrackList();
		
		MenuItem menuFileNewProject = new MenuItem("New JWave Project");
		menuFileNewProject.setOnAction(e -> browseForNewProject(stage, openJWaveListItems));
		menuFile.getItems().add(menuFileNewProject);
		
		Menu menuEdit = new Menu("Edit");
		Menu menuEffects = new Menu("Effects");
		Menu menuPreferences = new Menu("Preferences");
		menuBar.getMenus().addAll(menuFile,menuEdit,menuEffects,menuPreferences);
		
		((VBox) scene.getRoot()).getChildren().addAll(menuBar,openJWaveListView);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void browseForNewProject(Stage stage, ObservableList<String> openJWaveListItems) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Audio File");
		File chosen = fileChooser.showOpenDialog(stage);
		JWave newJWave = new JWave(chosen);
		tracks = new TrackList();
		tracks.add(newJWave);
		updateTrackList(stage, openJWaveListItems);
	}
	
	public void updateTrackList(Stage stage, ObservableList<String> openJWaveListItems) {
		for(JWave j: tracks.tracks) {
			openJWaveListItems.add(j.f.getAbsolutePath());
		}
	}
}
