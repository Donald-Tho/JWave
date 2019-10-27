package jwave.gui;

import jwave.core.*;

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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.KeyCode;


public class Gui extends Application{
	
   Button button;
   TrackList tracks;
   MediaPlayer mediaPlayer;
	
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
   	
      MenuItem menuFileImport = new MenuItem("Import");
      menuFileImport.setOnAction(e -> importNewFile(stage, openJWaveListItems));
      menuFile.getItems().add(menuFileImport);
      
      MenuItem menuFileExport = new MenuItem("Export");
      menuFileExport.setOnAction(e -> MenuFileExportGui());
      menuFile.getItems().add(menuFileExport);
      
      MenuItem menuFilePlay = new MenuItem("Play");
      menuFilePlay.setOnAction(e -> PlayGui());
      menuFile.getItems().add(menuFilePlay);
   	
      Menu menuEdit = new Menu("Edit");
      Menu menuEffects = new Menu("Effects");
      
      MenuItem menuEffectsChangeSpeed = new MenuItem("Change Speed");
      menuEffectsChangeSpeed.setOnAction(e -> ChangeSpeedGui());
      menuEffects.getItems().add(menuEffectsChangeSpeed);
      
      MenuItem menuEffectsChorus = new MenuItem("Chorus");
      menuEffectsChorus.setOnAction(e -> ChorusGui());
      menuEffects.getItems().add(menuEffectsChorus);
   	
      MenuItem menuEffectsNormalize = new MenuItem("Normalize");
      menuEffectsNormalize.setOnAction(e -> NormalizeGui());
      menuEffects.getItems().add(menuEffectsNormalize);
      
      Menu menuPreferences = new Menu("Preferences");
      
      menuBar.getMenus().addAll(menuFile,menuEdit,menuEffects,menuPreferences);
      
   	HBox homeRow = new HBox();
      
      Button homeRowPlay = new Button("Play");
      homeRowPlay.setOnAction(e -> PlayGui());
      
      Button homeRowStop = new Button("Stop");
      homeRowStop.setOnAction(e -> mediaPlayer.stop());
      
      homeRow.getChildren().addAll(homeRowPlay, homeRowStop);
      
      ((VBox) scene.getRoot()).getChildren().addAll(menuBar,homeRow, openJWaveListView);
   	
      scene.setOnKeyPressed(e -> {
           if (e.getCode() == KeyCode.DELETE) {
               System.out.println("A key was pressed");
               String item = openJWaveListView.getSelectionModel().getSelectedItem();
               for(int i = 0; i < tracks.tracks.size(); i++){
                  if(tracks.tracks.get(i).f.getAbsolutePath().equals(item)){
                     tracks.tracks.remove(i);
                     updateTrackList(stage, openJWaveListItems);
                     break;
                  }
               }
           }
      });
      
      
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
	
   public void importNewFile(Stage stage, ObservableList<String> openJWaveListItems) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select Audio File");
      File chosen = fileChooser.showOpenDialog(stage);
      JWave newJWave = new JWave(chosen);
      tracks.add(newJWave);
      updateTrackList(stage, openJWaveListItems);
   }
	
   public void updateTrackList(Stage stage, ObservableList<String> openJWaveListItems) {
      openJWaveListItems.clear();
      for(JWave j: tracks.tracks) {
         openJWaveListItems.add(j.f.getAbsolutePath());
      }
   }
   
   public void ChangeSpeedGui() {
      Scene scene = new Scene(new VBox(), 400, 400);
      ListView<String> openJWaveListView = new ListView<String>();
      ObservableList<String> openJWaveListItems = FXCollections.observableArrayList();
      openJWaveListView.setItems(openJWaveListItems);
      openJWaveListView.setPrefHeight(100.0);
      openJWaveListView.setPrefWidth(300.0);
      TextField speedMultiplier = new TextField();
      Button submit = new Button("Change Speed");
      submit.setOnMouseClicked(
         e -> {
            String item = openJWaveListView.getSelectionModel().getSelectedItem();
            for(JWave track: tracks.tracks){
               if(track.f.getAbsolutePath().equals(item)){
                  System.out.println(track.getAttributes());
                  Effect.changeSpeed(track, new Double(speedMultiplier.getText()));
                  System.out.println(track.getAttributes());
                  break;
               }
            }
         });
      ((VBox) scene.getRoot()).getChildren().addAll(openJWaveListView, speedMultiplier, submit);
      
      Stage stage = new Stage();
      stage.setTitle("Change Speed");
      updateTrackList(stage, openJWaveListItems);
      stage.setScene(scene);
      stage.show();
   }
   
   public void ChorusGui() {
      Scene scene = new Scene(new VBox(), 400, 400);
      ListView<String> openJWaveListView = new ListView<String>();
      ObservableList<String> openJWaveListItems = FXCollections.observableArrayList();
      openJWaveListView.setItems(openJWaveListItems);
      openJWaveListView.setPrefHeight(100.0);
      openJWaveListView.setPrefWidth(300.0);
      TextField delay = new TextField();
      Button submit = new Button("Chorus");
      submit.setOnMouseClicked(
         e -> {
            String item = openJWaveListView.getSelectionModel().getSelectedItem();
            for(JWave track: tracks.tracks){
               if(track.f.getAbsolutePath().equals(item)){
                  System.out.println(track.getAttributes());
                  Effect.chorus(track, new Double(delay.getText()));
                  System.out.println(track.getAttributes());
                  break;
               }
            }
         });
      ((VBox) scene.getRoot()).getChildren().addAll(openJWaveListView, delay, submit);
      Stage stage = new Stage();
      stage.setTitle("Chorus");
      updateTrackList(stage, openJWaveListItems);
      stage.setScene(scene);
      stage.show();
   }
   
   public void NormalizeGui() {
      Scene scene = new Scene(new VBox(), 400, 400);
      ListView<String> openJWaveListView = new ListView<String>();
      ObservableList<String> openJWaveListItems = FXCollections.observableArrayList();
      openJWaveListView.setItems(openJWaveListItems);
      openJWaveListView.setPrefHeight(100.0);
      openJWaveListView.setPrefWidth(300.0);
      Button submit = new Button("Normalize");
      submit.setOnMouseClicked(
         e -> {
            String item = openJWaveListView.getSelectionModel().getSelectedItem();
            System.out.println(item);
            for(JWave track: tracks.tracks){
               if(track.f.getAbsolutePath().equals(item)){
                  Effect.normalize(track);
                  break;
               }
            }
         });
      ((VBox) scene.getRoot()).getChildren().addAll(openJWaveListView, submit);
      Stage stage = new Stage();
      stage.setTitle("Normalize");
      updateTrackList(stage, openJWaveListItems);
      stage.setScene(scene);
      stage.show();
   }
   
   public void PlayGui() {
      JWave output = tracks.getMixedTrack();
      output.writeAllData("temp.wav");
      String bip = "temp.wav";
      Media hit = new Media(new File(bip).toURI().toString());
      mediaPlayer = new MediaPlayer(hit);
      mediaPlayer.play();
      System.out.println("hi");
   }
   
   public void MenuFileExportGui() {
      Scene scene = new Scene(new VBox(), 400, 400);
      Stage stage = new Stage();
      stage.setTitle("Export");
      stage.setScene(scene);
      ListView<String> openJWaveListView = new ListView<String>();
      ObservableList<String> openJWaveListItems = FXCollections.observableArrayList();
      openJWaveListView.setItems(openJWaveListItems);
      openJWaveListView.setPrefHeight(100.0);
      openJWaveListView.setPrefWidth(300.0);
      Button submit = new Button("Export");
      submit.setOnMouseClicked(
         e -> {
            String item = openJWaveListView.getSelectionModel().getSelectedItem();
            for(JWave track: tracks.tracks){
               if(track.f.getAbsolutePath().equals(item)){
                  FileChooser fileChooser = new FileChooser();
                  fileChooser.setTitle("Export Audio File");
                  File chosen = fileChooser.showSaveDialog(stage);
                  JWave output = new JWave();
                  output.setAttributes(track);
                  output.data = track.data;
                  System.out.println(output.getAttributes());
                  System.out.println(output.data.length);
                  output.writeAllData(chosen.getAbsolutePath());
               }
            }
         });
      ((VBox) scene.getRoot()).getChildren().addAll(openJWaveListView, submit);
      updateTrackList(stage, openJWaveListItems);
      stage.show();
   }
}
