package hu.petrik.budavari_dominik_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Date;

public class CreateCarController extends Controller {
    @FXML
    private TextField NameField;
    @FXML
    private TextField FuelField;
    @FXML
    private TextField ElectricField;
    @FXML
    private Spinner<Integer> ProductionField;
    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 30);
        ProductionField.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String Name = NameField.getText().trim();
        String Fuel = FuelField.getText().trim();
        Boolean Electric = Boolean.valueOf(ElectricField.getText().trim());
        Date Production =   new Date();
        if (Name.isEmpty()) {
            warning("Name is required");
            return;
        }
        if (Fuel.isEmpty()) {
            warning("Email is required");
            return;
        }
        if (Electric.isEmpty()) {
            warning("Electric is required");
            return;
        }
        // TODO: validate email format
        Car newCar = new Car(0, Fuel, Electric, Production);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newCar);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                warning("Person added!");
                NameField.setText("");
                FuelField.setText("");
                ElectricField.setText("");
                ProductionField.getValueFactory().setValue(30);
            } else {
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
