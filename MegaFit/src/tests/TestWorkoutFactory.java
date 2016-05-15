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
import static org.hamcrest.CoreMatchers.instanceOf;
import parser.ExerciseInfo;

public class TestWorkoutFactory {
	
	private WorkoutFactory factory;
	@Before
	public void setUp() {
		
		ExerciseInfo hammerCurls = new ExerciseInfo("Hammer Curl", 3, 5, 40);
		ExerciseInfo pushUps = new ExerciseInfo ("Push Ups", 5,3,3);
		List<ExerciseInfo> exerciseList = new ArrayList<ExerciseInfo>();
		exerciseList.add(hammerCurls);
		exerciseList.add(pushUps);
		
		factory = new WorkoutFactory(exerciseList);		
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
		assertThat(factory.getWorkoutPresentation(), instanceOf(PresentationFx.class));
		//assertEquals(factory.getWorkoutPresentation());
	}
		
	

}
