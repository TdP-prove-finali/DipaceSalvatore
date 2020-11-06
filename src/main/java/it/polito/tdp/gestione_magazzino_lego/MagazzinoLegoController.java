package it.polito.tdp.gestione_magazzino_lego;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.gestione_magazzino_lego.model.Model;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Part;
import it.polito.tdp.gestione_magazzino_lego.model.bean.PartView;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Set;
import it.polito.tdp.gestione_magazzino_lego.model.bean.Theme;
import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MagazzinoLegoController implements Initializable {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="gmThemesList"
	private ComboBox<Theme> gmThemesList; // Value injected by FXMLLoader

	@FXML // fx:id="gmSetsList"
	private ComboBox<Set> gmSetsList; // Value injected by FXMLLoader

	@FXML // fx:id="gmLoadSetButton"
	private Button gmLoadSetButton; // Value injected by FXMLLoader

	@FXML // fx:id="gmCoperturaButton"
	private Button gmCoperturaButton; // Value injected by FXMLLoader

	@FXML // fx:id="gmPieChart"
	private PieChart gmPieChart; // Value injected by FXMLLoader

	@FXML // fx:id="riThemesList"
	private ListView<Theme> riThemesList; // Value injected by FXMLLoader

	@FXML // fx:id="riSelectThemesButton"
	private Button riSelectThemesButton; // Value injected by FXMLLoader

	@FXML // fx:id="riSelectedThemes"
	private TextArea riSelectedThemes; // Value injected by FXMLLoader

	@FXML // fx:id="riPercentualeCompletamento"
	private Slider riPercentualeCompletamento; // Value injected by FXMLLoader

	@FXML // fx:id="riGetBestSequence"
	private Button riGetBestSequence; // Value injected by FXMLLoader

	@FXML // fx:id="riAzzeraSelezioneButton"
	private Button riAzzeraSelezioneButton; // Value injected by FXMLLoader

	@FXML // fx:id="riResult"
	private TextArea riResult; // Value injected by FXMLLoader

	@FXML // fx:id="gapThemesList"
	private ListView<Theme> gapThemesList; // Value injected by FXMLLoader

	@FXML // fx:id="gapSelectThemesButton"
	private Button gapSelectThemesButton; // Value injected by FXMLLoader

	@FXML // fx:id="gapSelectedThemes"
	private TextArea gapSelectedThemes; // Value injected by FXMLLoader

	@FXML // fx:id="gapPercentualeAccoppiamento"
	private Slider gapPercentualeAccoppiamento; // Value injected by FXMLLoader

	@FXML // fx:id="gapCreateGraphButton"
	private Button gapCreateGraphButton; // Value injected by FXMLLoader

	@FXML // fx:id="gapAzzeraSelezioneButton"
	private Button gapAzzeraSelezioneButton; // Value injected by FXMLLoader

	@FXML // fx:id="gapSetsGraph"
	private ComboBox<Set> gapSetsGraph; // Value injected by FXMLLoader

	@FXML // fx:id="gapAlberoVisitaResult"
	private TextArea gapAlberoVisitaResult; // Value injected by FXMLLoader

	@FXML // fx:id="gapMissingPartsResult"
	private TableView<PartView> gapMissingPartsResult; // Value injected by FXMLLoader

    @FXML // fx:id="gapCodePartInMissingPartsTable"
    private TableColumn<PartView, String> gapCodePartInMissingPartsTable; // Value injected by FXMLLoader

    @FXML // fx:id="gapColorPartInMissingPartsTable"
    private TableColumn<PartView, String> gapColorPartInMissingPartsTable; // Value injected by FXMLLoader
    
    @FXML // fx:id="gapMaterialPartInMissingPartsTable"
    private TableColumn<PartView, String> gapMaterialPartInMissingPartsTable; // Value injected by FXMLLoader
    
    @FXML // fx:id="gapQuantityPartInMissingPartsTable"
    private TableColumn<PartView, Integer> gapQuantityPartInMissingPartsTable; // Value injected by FXMLLoader

    @FXML // fx:id="gmResult"
    private TextArea gmResult;
	
	@FXML // fx:id="gapGetAlberoVisitaButton"
	private Button gapGetAlberoVisitaButton; // Value injected by FXMLLoader

	@FXML // fx:id="gapGetMissingPartsButton"
	private Button gapGetMissingPartsButton; // Value injected by FXMLLoader
	
	@FXML // fx:id="gapPercentualeGap"
    private Slider gapPercentualeGap; // Value injected by FXMLLoader

    @FXML // fx:id="gapSetsForGapButton"
    private Button gapSetsForGapButton; // Value injected by FXMLLoader

    @FXML // fx:id="gapSetsForGapResult"
    private TextArea gapSetsForGapResult; // Value injected by FXMLLoader

	@FXML
	void gapAzzeraSelezione(ActionEvent event) {
		this.gapSelectedThemes.clear();
		this.gapThemesSessionList.clear();
		this.gapSetsGraph.getItems().clear();
		this.gapCreateGraphButton.setDisable(true);
		this.gapSetsGraph.setDisable(true);
		this.gapAlberoVisitaResult.clear();
		this.gapGetMissingPartsButton.setDisable(true);
		this.gapGetAlberoVisitaButton.setDisable(true);
		this.gapSetsForGapButton.setDisable(true);
		this.gapMissingPartsResult.getItems().clear();
		this.gapSetsForGapResult.clear();
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
	    assert gmThemesList != null : "fx:id=\"gmThemesList\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gmSetsList != null : "fx:id=\"gmSetsList\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gmLoadSetButton != null : "fx:id=\"gmLoadSetButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gmCoperturaButton != null : "fx:id=\"gmCoperturaButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gmPieChart != null : "fx:id=\"gmPieChart\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gmResult != null : "fx:id=\"gmResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riThemesList != null : "fx:id=\"riThemesList\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riSelectThemesButton != null : "fx:id=\"riSelectThemesButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riSelectedThemes != null : "fx:id=\"riSelectedThemes\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riPercentualeCompletamento != null : "fx:id=\"riPercentualeCompletamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riGetBestSequence != null : "fx:id=\"riGetBestSequence\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riAzzeraSelezioneButton != null : "fx:id=\"riAzzeraSelezioneButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert riResult != null : "fx:id=\"riResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapThemesList != null : "fx:id=\"gapThemesList\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapSelectThemesButton != null : "fx:id=\"gapSelectThemesButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapSelectedThemes != null : "fx:id=\"gapSelectedThemes\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapPercentualeAccoppiamento != null : "fx:id=\"gapPercentualeAccoppiamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapCreateGraphButton != null : "fx:id=\"gapCreateGraphButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapAzzeraSelezioneButton != null : "fx:id=\"gapAzzeraSelezioneButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapSetsGraph != null : "fx:id=\"gapSetsGraph\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapGetAlberoVisitaButton != null : "fx:id=\"gapGetAlberoVisitaButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapGetMissingPartsButton != null : "fx:id=\"gapGetMissingPartsButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapAlberoVisitaResult != null : "fx:id=\"gapAlberoVisitaResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapMissingPartsResult != null : "fx:id=\"gapMissingPartsResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapCodePartInMissingPartsTable != null : "fx:id=\"gapCodePartInMissingPartsTable\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapColorPartInMissingPartsTable != null : "fx:id=\"gapColorPartInMissingPartsTable\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapMaterialPartInMissingPartsTable != null : "fx:id=\"gapMaterialPartInMissingPartsTable\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapQuantityPartInMissingPartsTable != null : "fx:id=\"gapQuantityPartInMissingPartsTable\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapPercentualeGap != null : "fx:id=\"gapPercentualeGap\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapSetsForGapButton != null : "fx:id=\"gapSetsForGapButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert gapSetsForGapResult != null : "fx:id=\"gapSetsForGapResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	private Model model;
	private List<Theme> riThemesSessionList = new ArrayList<Theme>();
	private List<Theme> gapThemesSessionList = new ArrayList<Theme>();
	private List<Set> gapAlberoSession = new ArrayList<Set>();
	private Map<String, Part> missingParts = new HashMap<String, Part>();
	
	
	
	@FXML
	void gmRetrieveSetsList(ActionEvent event) {
		Theme t = gmThemesList.getValue();
		if (t == null) {
			gmResult.appendText("Selezionare un tema\n");
			return;
		}
		gmSetsList.getItems().clear();

		List<Set> sets = new ArrayList<Set>();
		try {
			sets = getModel().listSetsByTheme(t);
		} catch (LegoException e) {
			gmResult.appendText("Non ci sono set per il tema " + t.getName() + "\n");
			return;
		}

		gmSetsList.getItems().addAll(sets);
		gmSetsList.setDisable(false);

	}

	@FXML
	void gmSelectedSetPostActions(ActionEvent event) {
		this.gmLoadSetButton.setDisable(false);
		this.gmCoperturaButton.setDisable(false);
	}

		
	
	@FXML
	void gmLoadSetInMagazzino(ActionEvent event) {
		Set set = gmSetsList.getValue();
		if (set == null) {
			gmResult.appendText("Selezionare un set.\n");
			return;
		}

		try {
			getModel().updateMagazzino(set);
			gmResult.appendText("Il set " + set.getName() + " è stato caricato correttamente in magazzino\n");
		} catch (LegoException e) {
			gmResult.appendText("Errore nel caricamento nel magazzino per il set selezionato\n");
			e.printStackTrace();
		}
	}

	@FXML
	void gmGetCopertura(ActionEvent event) {

		Set set = gmSetsList.getValue();
		if (set == null) {
			gmResult.appendText("Selezionare un set.\n");
			return;
		}

		try {
			float copertura = getModel().getCopertura(set);

			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					new PieChart.Data("pezzi in magazzino", copertura * 100),
					new PieChart.Data("pezzi da acquistare", (1 - copertura) * 100));

			gmPieChart.setData(pieChartData);

		} catch (LegoException e) {
			gmResult.appendText("Errore nel caricamento nel magazzino del set selezionato\n");
		}

	}

	@FXML
	void riSelectThemes(ActionEvent event) {
		this.riSelectedThemes.clear();

		ObservableList<Theme> selectedThemes = this.riThemesList.getSelectionModel().getSelectedItems();
		
		if(selectedThemes == null || selectedThemes.isEmpty()) {
			this.riResult.appendText("Selezionare un serie");
			return;
		}
		
		this.riThemesSessionList.addAll(selectedThemes);
		StringBuffer sbThemes = new StringBuffer();
		for (Theme t : riThemesSessionList) {
			sbThemes.append(t.getName()).append("\n");
		}

		this.riSelectedThemes.setText(this.riThemesSessionList.toString());
		this.riGetBestSequence.setDisable(false);
	}

	@FXML
	void gapSelectThemes(ActionEvent event) {
		this.gapSelectedThemes.clear();
		
		ObservableList<Theme> selectedThemes = this.gapThemesList.getSelectionModel().getSelectedItems();
		this.gapThemesSessionList.addAll(selectedThemes);
		StringBuffer sbThemes = new StringBuffer();
		for (Theme t : gapThemesSessionList) {
			sbThemes.append(t.getName()).append("\n");
		}

		this.gapSelectedThemes.setText(this.gapThemesSessionList.toString());
		this.gapCreateGraphButton.setDisable(false);
		}

	@FXML
	void riAzzeraSelezione(ActionEvent event) {
		this.riSelectedThemes.clear();
		this.riThemesSessionList.clear();
		this.riResult.clear();
	}

	@FXML
	void riCalcolaSequenze(ActionEvent event) {
		riResult.clear();
		int percentualeCompletamento = (int) this.riPercentualeCompletamento.getValue();

		if(this.riThemesSessionList == null || this.riThemesSessionList.isEmpty()) {
			riResult.appendText("Scegliere almeno una seria\n");
			return;
		}
		
		try {

			LocalDateTime executionTimeStart = LocalDateTime.now();
			List<List<Set>> setsList = getModel().risolviRicorsione(this.riThemesSessionList, percentualeCompletamento);
			LocalDateTime executionTimeEnd = LocalDateTime.now();
			long executionTime = ChronoUnit.MILLIS.between(executionTimeStart, executionTimeEnd);
			
			riResult.appendText("si calcolano le sequenze per i temi: " + this.riThemesSessionList + "\n");
			for (List<Set> sequenza : setsList) {
				riResult.appendText("Sequenza: " + sequenza + "\n");
			}
			
			riResult.appendText("Tempo di esecuzione dell'algoritmo: " + executionTime + " millisecondi");
			

		} catch (LegoException e) {
			riResult.appendText("Errore nel recupero dei set\n");
		}

	}

	public void gapCreateGraph(ActionEvent event) {
		this.gapAlberoVisitaResult.clear();
		this.gapSetsGraph.getItems().clear();
		this.gapSetsForGapResult.clear();
		this.gapMissingPartsResult.getItems().clear();
		try {
			getModel().createGraph(this.gapThemesSessionList, this.gapPercentualeAccoppiamento.getValue());
			List<Set> sets = getModel().getLinkedNodes();
			this.gapSetsGraph.getItems().addAll(sets);
			
			this.gapSetsGraph.setDisable(false);
			this.gapGetAlberoVisitaButton.setDisable(false);
		
		} catch (LegoException e) {
			this.gapAlberoVisitaResult.appendText("Errore nella creazione del grafo.");
		}

	}

	public void gapGetAlberoVisita(ActionEvent event) {
		gapAlberoVisitaResult.clear();
		Set source = this.gapSetsGraph.getValue();
		if(source == null) {
			this.gapAlberoVisitaResult.appendText("Scegliere un set");
			return;
		}
		
		this.gapAlberoSession = getModel().visitaAmpiezza(source);

		for (Set s : this.gapAlberoSession) {
			this.gapAlberoVisitaResult.appendText(String.format("%s [%s] \n", s.getName(), s.getCode()));
		}
		
		this.gapGetMissingPartsButton.setDisable(false);
	}

	public void gapGetMissingParts(ActionEvent event) {
		this.missingParts = getModel().retrieveMissingPartsInMagazzino(this.gapAlberoSession);

		
		this.gapCodePartInMissingPartsTable.setCellValueFactory(new PropertyValueFactory<PartView, String>("code"));
		this.gapColorPartInMissingPartsTable.setCellValueFactory(new PropertyValueFactory<PartView, String>("color"));
		this.gapMaterialPartInMissingPartsTable.setCellValueFactory(new PropertyValueFactory<PartView, String>("material"));
		this.gapQuantityPartInMissingPartsTable.setCellValueFactory(new PropertyValueFactory<PartView, Integer>("quantity"));
		
		ObservableList<PartView> ol = FXCollections.observableArrayList();
		for(Part p : this.missingParts.values()) {
			ol.add(new PartView(p.getCode(), p.getMaterial(), p.getColor().getName(), p.getQuantity()));
		}
		
	    this.gapMissingPartsResult.setItems(ol); 
	    this.gapMissingPartsResult.setDisable(false);
		this.gapSetsForGapButton.setDisable(false);
		
		
	}

	@FXML
    void gapRetrieveSetsForGap(ActionEvent event) {
		this.gapSetsForGapResult.clear();
		try {
			List<List<Set>> solutions = getModel().risolviRicorsioneForGap(this.missingParts, this.gapAlberoSession, this.gapPercentualeGap.getValue());
			if(solutions.isEmpty()) {
				this.gapSetsForGapResult.appendText("Non è possibile colmare il gap.\n");
			}
			
			
			for(List<Set> sets : solutions) {
				this.gapSetsForGapResult.appendText("Sequenza: " + sets + "\n");
			}

		
		} catch (LegoException e) {
			this.gapSetsForGapResult.appendText("Si è verificato un errore nella determinazione dei set minimi che colmano il gap\n");
		}
		
		
    }
	
	
	public void setModel(Model model) {
		this.model = model;

		try {
			Map<Long, Theme> themes = getModel().retrieveThemes();
			List<Theme> themesList = new ArrayList<Theme>(themes.values());

			Collections.sort(themesList);

			gmThemesList.getItems().addAll(themesList);
			riThemesList.getItems().addAll(themesList);
			riThemesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			gapThemesList.getItems().addAll(themesList);
			gapThemesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		} catch (LegoException e) {
			// txtResult.appendText("Errore caricamento temi\n");
			e.printStackTrace();
		}
	}

	private Model getModel() {
		return this.model;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
