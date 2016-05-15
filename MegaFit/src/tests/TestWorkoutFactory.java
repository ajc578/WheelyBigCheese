package tests;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



import userInterface.WorkoutFactory;
import presentationViewer.PresentationFx;
import presentationViewer.SlideContent;
import presentationViewer.SlideFx;
import presentationViewer.TextFx;
import static org.hamcrest.CoreMatchers.instanceOf;
import parser.ExerciseInfo;

public class TestWorkoutFactory {
	
	private PresentationFx presentation;
	private List<SlideFx> slideList;
	private WorkoutFactory factory;
	@Before
	public void setUp() {
		
		ExerciseInfo hammerCurls = new ExerciseInfo("Hammer Curl", 3, 5, 40);
		ExerciseInfo pushUps = new ExerciseInfo ("Push Ups", 5,3,3);
		List<ExerciseInfo> exerciseList = new ArrayList<ExerciseInfo>();
		exerciseList.add(hammerCurls);
		exerciseList.add(pushUps);
		
		factory = new WorkoutFactory(exerciseList);
		presentation = factory.getWorkoutPresentation();
		slideList = factory.getWorkoutSlideList();
		
	}
	
	@Test
	public void testCreateWorkoutFactory(){
		assertThat(factory, instanceOf(WorkoutFactory.class));
	}
	
	@Test
	public void testExerciseListPopulated(){
		assertEquals(factory.getExerciseList().size(), 2);
	}
	
	@Test
	public void testCreatePresentation(){
		
		
		assertThat(presentation, instanceOf(PresentationFx.class));
		
		
	}
	
	@Test
	public void testPresentationContainsContent(){
		assertEquals(slideList.size(), 2);
		assertEquals(slideList.get(0).getElements().get(0).getClass(), TextFx.class);
		assertEquals(((TextFx) slideList.get(0).getElements().get(0)).getText(), "Hammer Curl");
	}
		
	

}
