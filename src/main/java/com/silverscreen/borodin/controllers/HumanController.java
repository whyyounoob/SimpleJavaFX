package com.silverscreen.borodin.controllers;

import com.silverscreen.borodin.model.Human;
import com.silverscreen.borodin.service.HumanService;
import com.silverscreen.borodin.service.HumanServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static com.silverscreen.borodin.controllers.AlertController.showAlert;

public class HumanController implements Initializable {

  @FXML private DatePicker dateField;

  @FXML private TextField nameTextField;

  @FXML private Button saveButton;

  @FXML private Button cancelButton;

  private HumanService humanService = new HumanServiceImpl();

  private Human currentHuman;

  public void setCurrentHuman(Human currentHuman) {
    this.currentHuman = currentHuman;
    if (currentHuman == null) {
      dateField.setValue(LocalDate.now());
    } else {
      nameTextField.setText(currentHuman.getName());
      dateField.setValue(
          currentHuman.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
  }

  public void initialize(URL location, ResourceBundle resources) {
    dateField.setDayCellFactory(getDayCellFactory());
  }

  @FXML
  private void saveButtonAction() {
    if (nameTextField.getText().trim().equals("")) {
      showAlert(Alert.AlertType.WARNING, "Empty field", null, "Please, fill in the field name");
    } else {
      Human human =
          Human.builder()
              .birthday(
                  Date.from(
                      dateField
                          .getValue()
                          .atStartOfDay()
                          .atZone(ZoneId.systemDefault())
                          .toInstant()))
              .name(nameTextField.getText())
              .build();
      if (currentHuman == null) {
        humanService.save(human);
      } else {
        human.setId(currentHuman.getId());
        humanService.update(human);
        currentHuman = null;
      }
      Stage stage = (Stage) saveButton.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private void cancelButtonAction() {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  private Callback<DatePicker, DateCell> getDayCellFactory() {

    return new Callback<DatePicker, DateCell>() {

      @Override
      public DateCell call(final DatePicker datePicker) {
        return new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item.isAfter(LocalDate.now())) {
              setDisable(true);
              setStyle("-fx-background-color: rgba(0,0,0,0.34);");
            }
          }
        };
      }
    };
  }

}
