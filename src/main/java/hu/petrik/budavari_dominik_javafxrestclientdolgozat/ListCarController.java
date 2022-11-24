package hu.petrik.budavari_dominik_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class ListCarController extends Controller {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Car> carsTable;
    @FXML
    private TableColumn<Car, String> NameCol;
    @FXML
    private TableColumn<Car, String> FuelCol;
    @FXML
    private TableColumn<Car, Boolean> ElectricCol;
    @FXML
    private TableColumn<Car, Date> ProductionCol;

    @FXML
    private void initialize() {
        // getName() függvény eredményét írja ki
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        FuelCol.setCellValueFactory(new PropertyValueFactory<>("Fuel"));
        ElectricCol.setCellValueFactory(new PropertyValueFactory<>("Electric"));
        ProductionCol.setCellValueFactory(new PropertyValueFactory<>("Production"));
        Platform.runLater(() -> {
            try {
                loadCarFromServer();
            } catch (IOException e) {
                error("Couldn't get data from server", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void loadCarFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Car[] cars = converter.fromJson(content, Car[].class);
        carsTable.getItems().clear();
        for (Car Car : cars) {
            carsTable.getItems().add(Car);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-car-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create People");
            stage.setScene(scene);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnCloseRequest(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadCarFromServer();
                } catch (IOException e) {
                    error("An error occurred while communicating with the server");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        int selectedIndex = carsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a car from the list first");
            return;
        }
        Car selected = carsTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-car-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Update People");
            stage.setScene(scene);
            UpdateCarController controller = fxmlLoader.getController();
            controller.setCar(selected);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnHidden(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadCarFromServer();
                } catch (IOException e) {
                    error("An error occurred while communicating with the server");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectedIndex = carsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a car from the list first");
            return;
        }

        Car selected = carsTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are you sure you want to delete %s?", selected.getName()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()) {
            System.err.println("Unknown error occurred");
            return;
        }
        ButtonType clickedButton = optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)) {
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadCarFromServer();
            } catch (IOException e) {
                error("An error occurred while communicating with the server");
            }
        }
    }
}
