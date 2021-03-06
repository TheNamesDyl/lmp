package MP;


import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import FXTools.FXButtons;
import FXTools.FXListView;
import FXTools.FXSearch;
import FXTools.FXSlider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FXController extends Application implements Initializable{
	public PlayerModel model = new PlayerModel();
	public PlayerController controller = new PlayerController(model);
	private Task<Void> sliderThread;
	private Stage primaryStage = new Stage();
	private File[] fileCollection = model.getFileCollection();
	List<String> list = new ArrayList<String>();
	private int mostRecentSelectedIndex;
	ObservableList<String> observableList = FXCollections.observableList(list);
	private WebEngine webEngine;

	
	private FXSlider slider = new FXSlider(this, model);
	private FXSearch search = new FXSearch(this, model);
	private FXListView listview = new FXListView(this, model);
	private FXButtons buttons = new FXButtons(this, model);
	
	List<String> tempList = new ArrayList<String>();
	ObservableList<String> tempObservableList = FXCollections.observableList(tempList);
	
	private int indice;
	
	//using fxml this grabs all the variables
	@FXML Button shuffle, play, back, browseButton, downloadMusicButton;
	@FXML TextField artistPane, searchBar;
	@FXML Slider progressBar;
	@FXML ListView<String> songList;
	@FXML ProgressBar volumeBar;
	@FXML ImageView volumeImage;
	@FXML WebView webView;
	@FXML TextField youtubeLinkTextArea;
	@FXML SplitPane splitPane1, splitPane2, splitPane3, splitPane4, splitPane5, splitPane6;
    
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/Viewer.fxml"));
		Scene scene = new Scene(root);
		this.primaryStage = primaryStage;
		primaryStage.setTitle("HighQ Local Music Player");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		String css = this.getClass().getResource("../stylesheet.css").toExternalForm(); 
		scene.getStylesheets().add(css);
	}
	
	
	@FXML private void clickedPlayButton(){
		buttons.clickedPlayButton();
	}
	@FXML private void clickedShuffleButton(){
		buttons.clickedShuffleButton();
	}
	@FXML private void clickedBackButton(){
		buttons.clickedBackButton();
	}
	@FXML private void setOnVolumeClicked(MouseEvent event){
		slider.setOnVolumeClicked(event);
	}
	@FXML private void clickedBrowseButton(){
		buttons.clickedBrowseButton();
	}
	@FXML private void clickedDownloadMusicButton(){
		buttons.clickedDownloadMusicButton();
	}
	
	
	@FXML public void onKeyPressedOnSearch(){
		search.onKeyPressedOnSearch();
	}
	@FXML public void onMouseReleased(){
		slider.onMouseReleased();
	}
	@FXML public void onMousePressed(){
		slider.onMousePressed();
	}
	@FXML public void onDragDetected(){
		slider.onDragDetected();
	}
	
	
	//this is called if someone uses the browse to select a new directory
	public void resetDirectory(String path){
		if(model.isPlaying()){
			model.stopMusic();
		}
		model.resetMusicPath();
        model.setupMusic(path);
		listview.resetObservableList();
		progressBar.setOpacity(0);
		artistPane.setEditable(false);
		artistPane.setText("");
		artistPane.setPromptText("Click Browse Button to Select Music Folder");
		model.setFirstTimePlaying(true);
		if(sliderThread != null && sliderThread.isRunning()){
			slider.stopSliderThread();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//default settings
		listview.resetObservableList();
		listview.startSelectionListener();
		songList.setItems(observableList);
		progressBar.setOpacity(0);
		artistPane.setEditable(false);
		webEngine = webView.getEngine();
		webEngine.load("http://www.youtube.com");
	}
	

	//getters and setters
	public Task<Void> getSliderThread(){
		return sliderThread;
	}
	public void setSliderThread(Task<Void> sliderThread){
		this.sliderThread = sliderThread;
	}
	public File[] getFileCollection() {
		return fileCollection;
	}
	public void setFileCollection(File[] fileCollection) {
		this.fileCollection = fileCollection;
	}
	public ObservableList<String> getObservableList() {
		return observableList;
	}
	public void setObservableList(ObservableList<String> observableList) {
		this.observableList = observableList;
	}
	public List<String> getTempList() {
		return tempList;
	}
	public void setTempList(List<String> tempList) {
		this.tempList = tempList;
	}
	public ObservableList<String> getTempObservableList() {
		return tempObservableList;
	}
	public void setTempObservableList(ObservableList<String> tempObservableList) {
		this.tempObservableList = tempObservableList;
	}
	public Button getShuffle() {
		return shuffle;
	}
	public void setShuffle(Button shuffle) {
		this.shuffle = shuffle;
	}
	public Button getPlay() {
		return play;
	}
	public void setPlay(Button play) {
		this.play = play;
	}
	public Button getBack() {
		return back;
	}
	public void setBack(Button back) {
		this.back = back;
	}
	public TextField getArtistPane() {
		return artistPane;
	}
	public void setArtistPane(TextField artistPane) {
		this.artistPane = artistPane;
	}
	public TextField getSearchBar() {
		return searchBar;
	}
	public void setSearchBar(TextField searchBar) {
		this.searchBar = searchBar;
	}
	public Slider getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(Slider progressBar) {
		this.progressBar = progressBar;
	}
	public ListView<String> getSongList() {
		return songList;
	}
	public void setSongList(ListView<String> songList) {
		this.songList = songList;
	}
	public PlayerController getController() {
		return controller;
	}
	public void setController(PlayerController controller) {
		this.controller = controller;
	}
	public FXSlider getFXSlider() {
		return slider;
	}
	public void setFXSlider(FXSlider slider) {
		this.slider = slider;
	}
	public FXButtons getFXButtons() {
		return buttons;
	}
	public void setFXButtons(FXButtons buttons) {
		this.buttons = buttons;
	}
	public FXSearch getFXSearch() {
		return search;
	}
	public void setFXSearch(FXSearch search) {
		this.search = search;
	}
	public FXListView getFXListview() {
		return listview;
	}
	public void setFXListview(FXListView listview) {
		this.listview = listview;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public ProgressBar getVolumeBar() {
		return volumeBar;
	}
	public void setVolumeBar(ProgressBar volumeBar) {
		this.volumeBar = volumeBar;
	}

	public ImageView getVolumeImage() {
		return volumeImage;
	}

	public void setVolumeImage(ImageView volumeImage) {
		this.volumeImage = volumeImage;
	}

	public Button getBrowseButton() {
		return browseButton;
	}

	public void setBrowseButton(Button browseButton) {
		this.browseButton = browseButton;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public Button getDownloadMusicButton() {
		return downloadMusicButton;
	}
	public void setDownloadMusicButton(Button downloadMusicButton) {
		this.downloadMusicButton = downloadMusicButton;
	}
	public WebView getWebView() {
		return webView;
	}
	public void setWebView(WebView webView) {
		this.webView = webView;
	}
	public TextField getYoutubeLinkTextArea() {
		return youtubeLinkTextArea;
	}
	public void setYoutubeLinkTextArea(TextField youtubeLinkTextArea) {
		this.youtubeLinkTextArea = youtubeLinkTextArea;
	}

	public WebEngine getWebEngine() {
		return webEngine;
	}

	public void setWebEngine(WebEngine webEngine) {
		this.webEngine = webEngine;
	}
	

}
