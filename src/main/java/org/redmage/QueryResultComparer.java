package org.redmage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.redmage.csv_reader.CsvReader;
import org.redmage.csv_writer.CsvWriter;
import org.redmage.map_matcher.MapMatcher;

import java.io.File;
import java.util.Map;


public class QueryResultComparer extends Application {

    private File referenceFile;
    private File comparedFile;
    private File resultsFile;

    private Label resultsLabel;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            // set title for the stage
            primaryStage.setTitle("CSV Comparator");

            // create a File chooser
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter csvExtensionFilter =
                    new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");

            fileChooser.setSelectedExtensionFilter(csvExtensionFilter);



            // create Labels
            Label referenceFileLabel = new Label("No reference file selected");

            Label comparableFileLabel = new Label("No comparable file selected");

            Label resultsFileLabel = new Label("No results file selected");

            // create a Button
            Button referenceFileButton = new Button("Show 'reference file' open dialog");

            // create an Event Handler
            EventHandler<ActionEvent> selectReferenceFileEvent =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // get the file selected
                            referenceFile = fileChooser.showOpenDialog(primaryStage);

                            if (referenceFile != null) {
                                referenceFileLabel.setText(referenceFile.getAbsolutePath() + " selected!");
                            }
                        }
                    };

            referenceFileButton.setOnAction(selectReferenceFileEvent);

            // create a Button
            Button comparableFileButton = new Button("Show 'comparable file' open dialog");

            // create an Event Handler
            EventHandler<ActionEvent> selectcomparableFileEvent =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // get the file selected
                            comparedFile = fileChooser.showOpenDialog(primaryStage);

                            if (comparedFile != null) {
                                comparableFileLabel.setText(comparedFile.getAbsolutePath() + " selected!");
                            }
                        }
                    };

            comparableFileButton.setOnAction(selectcomparableFileEvent);

            // create a Button
            Button resultsFileButton = new Button("Show 'results file' save dialog");

            // create an Event Handler
            EventHandler<ActionEvent> resultsFileSaveEvent =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // Get the file selected
                            resultsFile = fileChooser.showSaveDialog(primaryStage);

                            if (resultsFile != null) {
                                resultsFileLabel.setText(resultsFile.getAbsolutePath() + " selected!");
                            }
                        }
                    };

            resultsFileButton.setOnAction(resultsFileSaveEvent);

            Button processButton = new Button("Process files");

            EventHandler<ActionEvent> processEvent =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        process(referenceFile, comparedFile, resultsFile);
                    }
                };

            processButton.setOnAction(processEvent);

            resultsLabel = new Label("");

            // create a VBox
            VBox vBox = new VBox(30,
                    referenceFileLabel,
                    referenceFileButton,
                    comparableFileLabel,
                    comparableFileButton,
                    resultsFileLabel,
                    resultsFileButton,
                    processButton,
                    resultsLabel);

            // set Alignment
            vBox.setAlignment(Pos.CENTER);

            // create a scene
            Scene scene = new Scene(vBox, 800, 500);

            // set the scene
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void process(File referenceFile, File comparedFile, File resultsFile) {

        Map<String, String> mapA = CsvReader.read(referenceFile.getPath());
        resultsLabel.setText("Reading reference file...");

        Map<String, String> mapB = CsvReader.read(comparedFile.getPath());
        resultsLabel.setText("Reading compared file...");

        Map<String, String> resultsMap = MapMatcher.match(mapA, mapB);
        resultsLabel.setText("Matching data...");

        resultsLabel.setText("Matching complete...");

        resultsLabel.setText("Writing to output file...");
        CsvWriter.write(resultsFile.getPath(), resultsMap);

        resultsLabel.setText("Writing complete...");
    }
}
