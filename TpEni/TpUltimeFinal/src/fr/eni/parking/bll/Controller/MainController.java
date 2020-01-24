package fr.eni.parking.bll.Controller;

import fr.eni.parking.ExceptionPerso.ExceptionBll;
import fr.eni.parking.bll.manager.FormateurManager;
import fr.eni.parking.bll.manager.VoitureManager;
import fr.eni.parking.bo.Formateur;
import fr.eni.parking.bo.Voiture;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import logger.LoggerPerso;

import java.io.*;
import java.util.logging.Logger;

public class MainController extends Application {

    //region Propriete
    private static Logger LOGGER = LoggerPerso.getLogger("MainController");
    VoitureManager voitureManager = VoitureManager.getInstance();
    FormateurManager formateurManager = FormateurManager.getInstance();

    @FXML
    TextField TfNomVoiture  ;
    @FXML
    TextField TfPiVoiture ;
    @FXML
    TableView<Voiture> TvVoitures ;
    @FXML
    TableColumn<Voiture,String> TcNomVoiture;
    @FXML
    TableColumn<Voiture,String> TcPiVoiture ;
    @FXML
    TableColumn<Voiture,Formateur> TcNomPrenomFormateurVoiture;
    @FXML
    TableView<Formateur> TvFormateurs;
    @FXML
    TableColumn<Formateur,String> TcPrenomFormateur;
    @FXML
    TableColumn<Formateur,String> TcNomFormateur;
    @FXML
    TextField TfPrenomFormateur;
    @FXML
    TextField TfNomFormateur;
    @FXML
    Button BModifierFormateur;
    @FXML
    ComboBox<Formateur> CbNomFormateur;
    //endregion

    //region Start

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../ihm/Main.fxml"));
        primaryStage.setTitle("Gestion parking");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public MainController() throws ExceptionBll {
    }

    public static void main(String[] args) {
        launch(args);
    }


    //endregion

    //region Fonction

    @FXML
    public void loadData(){
        TcPrenomFormateur.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        TcNomFormateur.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TvFormateurs.setItems(formateurManager.getListFormateurs());


        TcNomVoiture.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TcPiVoiture.setCellValueFactory(new PropertyValueFactory<>("pi"));
        TcNomPrenomFormateurVoiture.setCellValueFactory(new PropertyValueFactory<>("formateur"));
        TvVoitures.setItems(voitureManager.getListVoitures());

        ObservableList<Formateur> formateurs = formateurManager.getListFormateurs();
        formateurs.add(new Formateur());
        CbNomFormateur.setItems(formateurs);



    }
    @FXML
    private void initialize()
    {
        this.loadData();
    }

    public void OnClickAjouterVoiture(ActionEvent actionEvent) {
        String nom = TfNomVoiture.getText().trim();
        String pi = TfPiVoiture.getText();
        Voiture voiture = new Voiture(nom,pi,CbNomFormateur.getSelectionModel().getSelectedItem());

        try {
            voitureManager.insert(voiture);
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe("OnClickAjouterVoiture(ActionEvent actionEvent)  : "+exceptionBll.getMessage());
        }
    }

    public void OnClickAjouterFormateur(ActionEvent actionEvent) {
        try {
            formateurManager.insert(new Formateur(TfNomFormateur.getText(),TfPrenomFormateur.getText()));
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe(" OnClickAjouterFormateur(ActionEvent actionEvent) : " +exceptionBll.getMessage());
        }
    }

    public void OnClickModifierFormateur(ActionEvent actionEvent) {
        try {
            if (TvFormateurs.getSelectionModel().getSelectedItem() != null) {
                formateurManager.update(TvFormateurs.getSelectionModel().getSelectedItem(), TfNomFormateur.getText(), TfPrenomFormateur.getText());
                voitureManager.refresh();
            }
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe("OnClickModifier(ActionEvent actionEvent) : " +exceptionBll.getMessage());
        }
    }

    public void OnClickSelectedItemFormateur(MouseEvent mouseEvent) {
        Formateur formateurSelected = TvFormateurs.getSelectionModel().getSelectedItem();
        if (formateurSelected != null) {
            TfNomFormateur.setText(formateurSelected.getNom());
            TfPrenomFormateur.setText(formateurSelected.getPrenom());
        }
    }

    public void OnActionSupprimerFormateur(ActionEvent actionEvent) {
        try {
            if (TvFormateurs.getSelectionModel().getSelectedItem() != null){
                formateurManager.delete(TvFormateurs.getSelectionModel().getSelectedItem());
                voitureManager.refresh();
            }
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe(" OnActionSupprimerFormateur(ActionEvent actionEvent)  : " +exceptionBll.getMessage());
        }
    }

    public void OnClickModifierVoiture(ActionEvent actionEvent) {
        try {
            if (TvVoitures.getSelectionModel().getSelectedItem() != null)
                voitureManager.update(TvVoitures.getSelectionModel().getSelectedItem(),TfNomVoiture.getText(),TfPiVoiture.getText(),CbNomFormateur.getSelectionModel().getSelectedItem());
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe("Erreur dans OnClickModifierVoiture(ActionEvent actionEvent) : " + exceptionBll.getMessage());
        }
    }

    public void OnClickSupprimerVoiture(ActionEvent actionEvent) {
        try {
            if (TvVoitures.getSelectionModel().getSelectedItem() != null)
                voitureManager.delete(TvVoitures.getSelectionModel().getSelectedItem());
        } catch (ExceptionBll exceptionBll) {
            LOGGER.severe("Erreur dans OnClickSupprimerVoiture(ActionEvent actionEvent) : " + exceptionBll.getMessage());
        }
    }

    public void OnClickSelectedItemVoiture(MouseEvent mouseEvent) {
        Voiture voitureSelected = TvVoitures.getSelectionModel().getSelectedItem();
        if (voitureSelected != null) {
            TfNomVoiture.setText(voitureSelected.getNom());
            TfPiVoiture.setText(voitureSelected.getPi());
            CbNomFormateur.setValue(voitureSelected.getFormateur());
        }
    }

    public void OnActionExportXml(ActionEvent actionEvent) {
        try {
            voitureManager.serialiseur();
            formateurManager.serialiseur();
        }catch (ExceptionBll exceptionBll){
            LOGGER.severe(" OnActionExportXml(ActionEvent actionEvent)  : " +exceptionBll.getMessage());
        }
    }

    public void OnActionExportCsv(ActionEvent actionEvent) {
        Writer writer = null;

        try {
            File file = new File("./Export.csv");
            writer = new BufferedWriter(new FileWriter(file));

            String personneTextHeader = "[NOM;PRENOM]\n";
            writer.write(personneTextHeader);

            for ( Formateur formateur : formateurManager.getFormateurs()) {
                String personneText = formateur.getNom() + ";" + formateur.getPrenom() + "\n";

                writer.write(personneText);
            }

            String voitureTextHeader = "\n[NOM;PI;PERSONNE]\n";
            writer.write(voitureTextHeader);

            for (Voiture voiture : voitureManager.getVoitures()) {
                String voitureText = voiture.getNom() + ";" + voiture.getPi() + ";" + voiture.getFormateur() + "\n";

                writer.write(voitureText);
            }
        } catch (Exception e) {
            LOGGER.severe("OnActionExportCsv(ActionEvent actionEvent)  : " +e.getMessage());
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                LOGGER.severe("OnActionExportCsv(ActionEvent actionEvent)  : " +e.getMessage());
            }
        }
    }

    //endregion

}
