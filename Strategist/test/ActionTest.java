import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Action.Action;

public class ActionTest {

	@Before 
	
	@Test
	public void testAction() {
		Action action = new Action("take transition pointingmode.pointingmode22->pointingmode.pointingmode20 ", 
				"{ plan_clock >= 10 && pointingmode_clock >= 3 && R3_clock >= H + 0 && R3_clock <= H + 100, tau, "
				+ "pointingmode_clock := 0 }");
		assertEquals(action.getGuards().getGuard().contains("plan_clock"), true);
	}

	@Test
	public void testTakeTransition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInitialStates() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFinalStates() {
		Action action = new Action("take transition pointingmode.pointingmode22->pointingmode.pointingmode20 ", 
				"{ plan_clock >= 10 && pointingmode_clock >= 3 && R3_clock >= H + 0 && R3_clock <= H + 100, tau, "
				+ "pointingmode_clock := 0 }");
		assertEquals(action.getFinalStates().get("pointingmode"), "pointingmode22");
	}

	@Test
	public void testGetGuards() {
		Action action = new Action("take transition pointingmode.pointingmode22->pointingmode.pointingmode20 ", 
				"{ plan_clock >= 10 && pointingmode_clock >= 3 && R3_clock >= H + 0 && R3_clock <= H + 100, tau, "
				+ "pointingmode_clock := 0 }");
		assertEquals(action.getGuards().getGuard(), "plan_clock >= 10 && pointingmode_clock >= 3 &&"
				+ " R3_clock >= H + 0 && R3_clock <= H + 100");
	}

	@Test
	public void testGetNewValues() {
		Action action = new Action("take transition pointingmode.pointingmode22->pointingmode.pointingmode20 ", 
				"{ plan_clock >= 10 && pointingmode_clock >= 3 && R3_clock >= H + 0 && R3_clock <= H + 100, tau, "
				+ "pointingmode_clock := 0 }");
		assertEquals(action.getNewValues().getNewValues().contains("pointingmode_clock:=0"), true);
	}

}
