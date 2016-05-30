package userInterface;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import presentationViewer.PresentationFx;
import presentationViewer.SlideContent;
import presentationViewer.SlideFx;
import presentationViewer.TextFx;
import parser.ExerciseInfo;

public class WorkoutFactory {
	
	List<ExerciseInfo> exerciseList;
	List<SlideFx> slideList;
	PresentationFx workout;

	public WorkoutFactory(List<ExerciseInfo> exerciseList) {
		this.exerciseList = exerciseList;
		slideList = new ArrayList<SlideFx>();
		workout = new PresentationFx("Workout 1", null, null, null);
		
		for(int i = 0; i < exerciseList.size(); i++){
			ArrayList<SlideContent> slideContent = new ArrayList<SlideContent>();
			slideContent.add(new TextFx(0, 0, 0, 0,0,0, exerciseList.get(i).getName(), 
					"", 12, Color.AQUAMARINE, 0));
			slideList.add(new SlideFx(0, slideContent, 0, 0, null));
		}
			
	}

	public List<ExerciseInfo> getExerciseList() {
		// TODO Auto-generated method stub
		return exerciseList;
	}
	
	

	public PresentationFx getWorkoutPresentation() {
		// TODO Auto-generated method stub
		return workout;
	}

	public List<SlideFx> getWorkoutSlideList() {
		// TODO Auto-generated method stub
		return slideList;
	}

}
