package hu.petrik.budavari_dominik_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCarController extends Controller {
    @FXML
    private TextField NameField;
    @FXML
    private TextField FuelField;
    @FXML
    private TextField ElectricField;
    @FXML
    private Spinner<Integer> ProductionField;
    @FXML
    private Button updateButton;

    private Car Car;

    public void setCar(Car Car) {
        this.Car = Car;
        NameField.setText(this.Car.getName());
        FuelField.setText(this.Car.getFuel());
        ElectricField.setText(this.Car.getElectric());
        ProductionField.getValueFactory().setValue(this.Car.getProduction());
    }

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 30);
        ProductionField.setValueFactory(valueFactory);
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        String Name = NameField.getText().trim();
        String Fuel = FuelField.getText().trim();
        int Production = ProductionField.getValue();
        if (Name.isEmpty()) {
            warning("Name is required");
            return;
        }
        if (Fuel.isEmpty()) {
            warning("Email is required");
            return;
        }
        // TODO: validate email format
        this.Car.setName(Name);
        this.Car.setFuel(Fuel);
        this.Car.setProduction(Production);
        Gson converter = new Gson();
        String json = converter.toJson(this.Car);
        try {
            String url = App.BASE_URL + "/" + this.Car.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
