package FXTools;

import MP.FXController;
import MP.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class FXSearch {

	FXController fxcontroller;
	PlayerModel model;
	
	public FXSearch(FXController fxcontroller, PlayerModel model){
		this.fxcontroller = fxcontroller;
		this.model = model;
	}
	
	//this is triggered when a key is typed into search bar
	public void onKeyPressedOnSearch(){
		fxcontroller.setFileCollection(model.getFileCollection());
		fxcontroller.getTempObservableList().clear();
		
		//have to do this because its always behind a character otherwise.
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	
		    	if(!(fxcontroller.getSearchBar().getText().equals(null) || fxcontroller.getSearchBar().getText().equals(""))){
		    		for(int i = 0; i < model.getNumOfFiles(); i++){
		    			if(!(fxcontroller.getSearchBar().getText().length() > fxcontroller.getFileCollection()[i].getName().length())){
		    				if(fxcontroller.getSearchBar().getText().toLowerCase().equals(fxcontroller.getFileCollection()[i].getName().substring(0, fxcontroller.getSearchBar().getText().length()).toLowerCase())){
		    					fxcontroller.getTempObservableList().add(fxcontroller.getObservableList().get(i));
		    				}
		    			}
		    		}
		    		
		    		fxcontroller.getSongList().setItems(fxcontroller.getTempObservableList());
		    		fxcontroller.getFXListview().onMusicChangeSettings();
		    	}else{
		    		
		    		fxcontroller.getSongList().setItems(fxcontroller.getObservableList());
		    		fxcontroller.getFXListview().onMusicChangeSettings();
		    	}
				
		    }
		});
		
	}
	
}
