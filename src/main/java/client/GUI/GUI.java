package main.java.client.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import main.java.client.Client;
import main.java.server.models.Course;

import java.io.IOException;


public class GUI extends Application {

    private final int PORT = 1337;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Client client = new Client("127.0.0.1", 1337);
        Course[] courses;

        GridPane root = new GridPane();
        Scene scene = new Scene(root, 720,540);


        root.setStyle("-fx-background-color:Beige;");
        ColumnConstraints col = new ColumnConstraints();
        col.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(col, col);


        VBox coursePanel = new VBox();
        HBox sessionPanel = new HBox();
        GridPane formPanel = new GridPane();

        coursePanel.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: White");
        coursePanel.setAlignment(Pos.CENTER);
        coursePanel.setSpacing(10);
        coursePanel.prefWidthProperty().bind(root.widthProperty().multiply(0.5));

        sessionPanel.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: White");
        sessionPanel.setAlignment(Pos.CENTER);
        sessionPanel.setSpacing(40);

        formPanel.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: White");
        formPanel.setAlignment(Pos.CENTER);
        formPanel.setVgap(40);
        formPanel.setHgap(50);
        formPanel.prefWidthProperty().bind(root.widthProperty().multiply(0.5));

        //add children to root
        root.add(coursePanel, 0, 0, 1, 1);
        root.add(sessionPanel, 0, 1, 1, 1);
        root.add(formPanel, 1, 0, 1, 1);

        //coursePanel children
        Text courseTitle = new Text("Liste des cours");
        courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,20));
        courseTitle.setTextAlignment(TextAlignment.CENTER);

        TableView courseTable = new TableView();
        TableViewSelectionModel<Course> tableModel = courseTable.getSelectionModel();
        ObservableList<Course> data = FXCollections.observableArrayList();
        TableColumn codeCol = new TableColumn("Code");
        TableColumn courseCol = new TableColumn("Cours");

        courseTable.setEditable(true);
        courseTable.setItems(data);
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeCol.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.5));
        courseCol.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.5));
        courseTable.getColumns().addAll(codeCol, courseCol);

        coursePanel.getChildren().addAll(courseTitle, courseTable);

        //sessionPanel children
        String[] sessions = {"Automne", "Hiver", "Ete"};
        ComboBox sessionSelect = new ComboBox(FXCollections.observableArrayList(sessions));
        Button sessionButton = new Button("Charger");
        sessionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(sessionSelect.getValue() != null){
                    data.remove(0, data.size());
                    data.addAll(client.getCourses((String)sessionSelect.getValue()));
                }
            }
        });
        sessionPanel.getChildren().addAll(sessionSelect, sessionButton);

        //formPanel children
        Text formTitle = new Text("Formulaire d'inscription");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,20));
        formPanel.setHalignment(formTitle, HPos.CENTER);

        Label prenomLabel = new Label("Prénom:");
        Label nomLabel = new Label("Nom:");
        Label emailLabel = new Label("Email:");
        Label matriculeLabel = new Label("Matricule:");

        prenomLabel.setAlignment(Pos.CENTER_LEFT);
        nomLabel.setAlignment(Pos.CENTER_LEFT);
        emailLabel.setAlignment(Pos.CENTER_LEFT);
        matriculeLabel.setAlignment(Pos.CENTER_LEFT);

        TextField prenomField = new TextField();
        TextField nomField = new TextField();
        TextField emailField = new TextField();
        TextField matriculeField = new TextField();

        Text feedback = new Text("Veuillez selectionner un cours affiché dans le tableau");
        feedback.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,12));
        feedback.setWrappingWidth(formPanel.getWidth());
        formPanel.setHalignment(feedback, HPos.CENTER);

        Button sendButton = new Button("Envoyer");
        sendButton.setPrefWidth(100);
        formPanel.setHalignment(sendButton, HPos.CENTER);
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String msg = client.sendRegistration(prenomField.getText(), nomField.getText(), emailField.getText(),
                        matriculeField.getText(), tableModel.getSelectedItem());
                if(msg.charAt(0) == 'F'){
                    feedback.setStyle("-fx-fill: Black");
                }
                else{
                    feedback.setStyle("-fx-fill: Red");
                }
                feedback.setText(msg);
            }
        });

        formPanel.add(formTitle, 0, 0, 2, 1);
        formPanel.add(prenomLabel, 0, 1, 1, 1);
        formPanel.add(prenomField, 1, 1, 1, 1);
        formPanel.add(nomLabel, 0, 2, 1, 1);
        formPanel.add(nomField, 1, 2, 1, 1);
        formPanel.add(emailLabel, 0, 3, 1, 1);
        formPanel.add(emailField, 1, 3, 1, 1);
        formPanel.add(matriculeLabel, 0, 4, 1, 1);
        formPanel.add(matriculeField, 1, 4, 1, 1);
        formPanel.add(sendButton, 0, 5, 2, 1);
        formPanel.add(feedback, 0, 6, 2, 1);

        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}
