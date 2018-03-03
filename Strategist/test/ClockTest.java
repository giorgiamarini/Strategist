import static org.junit.Assert.*;

import org.junit.Test;

import Clock.Clock;

public class ClockTest {

	@Test
	public void testGetClockName() {
		assertEquals("plan_clock", (new Clock("plan_clock")).getClockName());
	}

	@Test
	public void testSetClockName() {
		Clock clock = new Clock("plan_clock"); 
		clock.setClockName("aaaa");
		assertEquals("aaaa", clock.getClockName());
	}

	@Test
	public void testGetTime() {
		assertEquals(0, (new Clock("a")).getTime());
	}

	@Test
	public void testSetTime() {
		Clock clock = new Clock("a"); 
		clock.setTime(11);
		assertEquals(11, clock.getTime());
	}

}
