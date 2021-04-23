/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<String> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	String mese=boxMese.getValue();
    	if(mese!=null) {
    		txtResult.clear();
    		List<Citta> sequenzaCitta=model.trovaSequenza(getMese(mese));
    		int i=1; 
    		for(Citta c:sequenzaCitta)
    			txtResult.appendText((i++)+" "+c.getNome()+"\n");
    	}
    	else {
    		txtResult.setText("ERRORE: mese non selezionato");
    	}

    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	String mese=boxMese.getValue();
    	if(mese!=null) {
    		txtResult.clear();
    		Map<String, Double> umiditaMedia=model.getUmiditaMedia(getMese(mese));
    		for(Map.Entry<String, Double> entry:umiditaMedia.entrySet()) {
    			txtResult.appendText(entry.getKey()+" "+entry.getValue()+"\n");
    		}
    	}
    	else {
    		txtResult.setText("ERRORE: mese non selezionato");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		boxMese.getItems().add("Gennaio");
		boxMese.getItems().add("Febbraio");
		boxMese.getItems().add("Marzo");
		boxMese.getItems().add("Aprile");
		boxMese.getItems().add("Maggio");
		boxMese.getItems().add("Giugno");
		boxMese.getItems().add("Luglio");
		boxMese.getItems().add("Agosto");
		boxMese.getItems().add("Settembre");
		boxMese.getItems().add("Ottobre");
		boxMese.getItems().add("Novembre");
		boxMese.getItems().add("Dicembre");
	}
	
	private int getMese(String mese) {
		
		if(mese.equals("Gennaio"))
    		return 1;
    	else if (mese.equals("Febbraio"))
    		return 2;
    	else if (mese.equals("Marzo"))
    		return 3;
    	else if (mese.equals("Aprile"))
    		return 4;
    	else if (mese.equals("Maggio"))
    		return 5;
    	else if (mese.equals("Giugno"))
    		return 6;
    	else if (mese.equals("Luglio"))
    		return 7;
    	else if (mese.equals("Agosto"))
    		return 8;
    	else if (mese.equals("Settembre"))
    		return 9;
    	else if (mese.equals("Ottobre"))
    		return 10;
    	else if (mese.equals("Novembre"))
    		return 11;
    	else if (mese.equals("Dicembre"))
    		return 12;
		
		return -1;
	}
}

