package com.silverscreen.borodin.controllers;

import com.silverscreen.borodin.model.Human;
import com.silverscreen.borodin.service.HumanService;
import com.silverscreen.borodin.service.HumanServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static com.silverscreen.borodin.controllers.AlertController.showAlert;

public class MainController implements Initializable {

  @FXML private Button addButton;

  @FXML private Button deleteButton;

  @FXML private Button editButton;

  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  @FXML private TreeTableView<Human> humanTable;
  @FXML private TreeTableColumn<Human, String> nameColumn;
  @FXML private TreeTableColumn<Human, String> ageColumn;
  @FXML private TreeTableColumn<Human, String> birthdayColumn;

  private HumanService humanService = new HumanServiceImpl();

  public void initialize(URL location, ResourceBundle resources) {
    initializeTable();
  }

  public void addHuman() throws IOException {
    showHumanWindow(null);
    initializeTable();
  }

  public void deleteHuman() {
    TreeItem<Human> selectedItem = humanTable.getSelectionModel().getSelectedItem();
    if (selectedItem == null) {
      showAlert(
          Alert.AlertType.INFORMATION,
          "Select item",
          null,
          "Please, select some item it the table");
    } else {
      humanService.delete(selectedItem.getValue().getId());
      initializeTable();
    }
  }

  public void editHuman() {
    TreeItem<Human> selectedItem = humanTable.getSelectionModel().getSelectedItem();
    if (selectedItem == null) {
      showAlert(
          Alert.AlertType.INFORMATION,
          "Select item",
          null,
          "Please, select some item it the table");
    } else {
      showHumanWindow(selectedItem.getValue());
      initializeTable();
    }
  }

  private void initializeTable() {
    humanTable.setRoot(null);
    TreeItem<Human> root = new TreeItem<>();
    nameColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getValue().getName()));
    ageColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getValue().getAge() + " y.o."));
    birthdayColumn.setCellValueFactory(
        param ->
            new SimpleStringProperty(
                simpleDateFormat.format(param.getValue().getValue().getBirthday())));
    humanService
        .getAll()
        .forEach(
            human -> {
              TreeItem<Human> item = new TreeItem<>(human);
              root.getChildren().add(item);
            });
    humanTable.setOnMouseClicked(
        event -> {
          if (event.getClickCount() == 2
              && humanTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Human> human = humanTable.getSelectionModel().getSelectedItem();
            Calendar now = Calendar.getInstance();
            Calendar birthday = Calendar.getInstance();
            now.setTime(new Date());
            birthday.setTime(human.getValue().getBirthday());
            if (now.get(Calendar.MONTH) == birthday.get(Calendar.MONTH)
                && now.get(Calendar.DAY_OF_MONTH) == birthday.get(Calendar.DAY_OF_MONTH)) {
              showAlert(
                  Alert.AlertType.INFORMATION,
                  "Birthday!",
                  null,
                  human.getValue().getName()
                      + " today celebrates birthday. Do not forget to congratulate this wonderful person on his birthday!");
            }
          }
        });
    humanTable.setRoot(root);
    humanTable.setShowRoot(false);
  }

  private void showHumanWindow(Human human) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HumanWindow.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      HumanController humanController = fxmlLoader.getController();
      humanController.setCurrentHuman(human);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOpacity(1);
      stage.setResizable(false);
      stage.setTitle("Human window");
      stage.setScene(new Scene(root));
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
