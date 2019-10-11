package myvcrime.NPE;

import java.util.ArrayList;

public class Guide {
	
	private ArrayList<Stage> stageList;
	private int currentIndex = 0;
	
	public Guide(){
	}
	
	public void addStage(Stage stage){
		this.stageList.add(stage);
	}
	
	public void start(){
		if(!(currentIndex >= stageList.size())){
			stageList.get(currentIndex).start();
		}
	}
	
	protected void nextStage(){
		currentIndex++;
		if(!(currentIndex >= stageList.size())){
			stageList.get(currentIndex).start();
		} else {
			//no guide
		}
	}
}
