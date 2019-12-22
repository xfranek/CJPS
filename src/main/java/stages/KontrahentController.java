package stages;

import dao.DokumentDao;
import dao.KontrahentDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Dokument;
import model.Kontrahent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class KontrahentController {
    private DodajNowyCertyfikatController dodajNowyCertyfikatController;

    @FXML
    private Button anulujButton;
    @FXML
    private TableView<Kontrahent> kontrahenciTableView;
    @FXML
    private TextField idKontrahentField;
    @FXML
    private TextField nazwaKontrahentField;
    @FXML
    private TextField adresKontrahentField;
    @FXML
    private TextField nipKontrahentField;
    @FXML
    private TextField regonKontrahentField;
    @FXML
    private Button okButton;
    @FXML
    private TextField szukajTextField;


    @FXML
    public void initialize() {

        addAllKontrahentListFromDatabaseToTableView();
        //  addNewKontrahentToTableViewTest();

    }

    @FXML //Pobierz dane zaznaczone w TableView
    protected void deleteRowFromTableViewAndDatabase(ActionEvent event) {

        KontrahentDao kontrahentDao = new KontrahentDao();
        System.out.println(kontrahenciTableView.getSelectionModel().getSelectedItem().getIdKontrahent());
        kontrahentDao.deleteKontrahentDatabase(kontrahenciTableView.getSelectionModel().getSelectedItem().getIdKontrahent());


        ObservableList<Kontrahent> data = kontrahenciTableView.getItems();
        data.remove(kontrahenciTableView.getSelectionModel().getSelectedItem());


    }

    public void dodajNowyKontrahent(Kontrahent kontrahent) {
        ObservableList<Kontrahent> data = kontrahenciTableView.getItems();
        data.add(new Kontrahent(kontrahent.getIdKontrahent(),
                kontrahent.getNazwaKontrahent(),
                kontrahent.getAdresKontrahent(),
                kontrahent.getNipKontrahent(),
                kontrahent.getRegonKontrahent()
        ));
        KontrahentDao kontrahentDao = new KontrahentDao();
        kontrahentDao.addKontrahentDatabase(kontrahent);

    }


    @FXML
    protected void dodajNowyOnClick(ActionEvent event) throws IOException {
//        ObservableList<Kontrahent> data = KontrahenciTableView.getItems();
////        data.add(new Kontrahent(idKontrahentField.getText(),
////                nazwaKontrahentField.getText(),
////                adresKontrahentField.getText(),
////                nipKontrahentField.getText(),
////                regonKontrahentField.getText()
////        ));
////        KontrahentDao kontrahentDao = new KontrahentDao();
////        kontrahentDao.addKontrahentDatabase(idKontrahentField.getText(), nazwaKontrahentField.getText(), adresKontrahentField.getText(), nipKontrahentField.getText(), regonKontrahentField.getText() );
////
////        idKontrahentField.setText("");
////        nazwaKontrahentField.setText("");
////        adresKontrahentField.setText("");
////        nipKontrahentField.setText("");
////        regonKontrahentField.setText("");

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/stages/DodajNowyKontrahent.fxml"));
        Pane pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Nowy kontrahent");
        DodajNowyKontrahentController dodajNowyKontrahentController = loader.getController();
        dodajNowyKontrahentController.kontrahentController = KontrahentController.this;
        stage.show();

    }

    @FXML
    protected void addNewKontrahentToTableViewTest() {
        ObservableList<Kontrahent> data = kontrahenciTableView.getItems();
        data.add(new Kontrahent("idKontrahentField.getText()",
                "nazwaKontrahentField.getText()",
                "adresKontrahentField.getText()",
                "nipKontrahentField.getText()",
                "regonKontrahentField.getText()"));

    }

    @FXML
    protected void addAllKontrahentListFromDatabaseToTableView() {

        KontrahentDao kontrahentDao = new KontrahentDao();
        List<Kontrahent> list = kontrahentDao.getAllKontrahent();

        Iterator<Kontrahent> iterator = list.iterator();

        for (Kontrahent b : list) {

            ObservableList<Kontrahent> data = kontrahenciTableView.getItems();
            data.add(new Kontrahent(b.idKontrahentProperty().getValue(),
                    b.nazwaKontrahentProperty().getValue(),
                    b.adresKontrahentProperty().getValue(),
                    b.nipKontrahentProperty().getValue(),
                    b.regonKontrahentProperty().getValue()));
        }
    }

    @FXML
    void okOnClick(ActionEvent event) {
        if (dodajNowyCertyfikatController != null) {
            dodajNowyCertyfikatController.setDostawcaField(getKontrahenciTableView().getSelectionModel().getSelectedItem().getIdKontrahent());
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        } else {
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void anulujOnClick(ActionEvent event) {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void szukaj(KeyEvent event) {
        ObservableList<Kontrahent> data = kontrahenciTableView.getItems();
        data.removeAll(data);
        KontrahentDao kontrachentDao = new KontrahentDao();
        List<Kontrahent> dataFromDB = kontrachentDao.getAllKontrahent();

        dataFromDB.stream()
                .filter(item -> item.toString().matches(("(.*)" + szukajTextField.getText()) + "(.*)"))
                .forEach(data::add);

        Collections.reverse(data);
    }


    public DodajNowyCertyfikatController getDodajNowyCertyfikatController() {
        return dodajNowyCertyfikatController;
    }

    public void setDodajNowyCertyfikatController(DodajNowyCertyfikatController dodajNowyCertyfikatController) {
        this.dodajNowyCertyfikatController = dodajNowyCertyfikatController;
    }

    public TableView<Kontrahent> getKontrahenciTableView() {
        return kontrahenciTableView;
    }
}