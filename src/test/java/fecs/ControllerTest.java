package fecs;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.interfaces.ICircumstance;
import fecs.simulator.Engine;
import fecs.ui.Controller;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControllerTest extends AbstractSpringBasedTestSupport {
  @Autowired
  @InjectMocks
  private Controller controller;

  @Mock
  private Engine engine;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testStartSimulation() throws Exception {

  }

  @Test
  public void testStopSimulation() throws Exception {

  }

  @Test
  public void testTriggerFail() throws Exception {
    // when
    when(engine.getState()).thenReturn(1);
    controller.triggerFail(ICircumstance.EARTH_QUAKE);

    // then
    verify(engine).setState(0x09);
  }

  @Test
  public void testChangeGravity() throws Exception {

  }

  @Test
  public void testChangeCabinLimitPeople() throws Exception {

  }

  @Test
  public void testChangeCabinWeight() throws Exception {

  }

  @Test
  public void testChangePassengerWeight() throws Exception {

  }

  @Test
  public void testChangeTotalNumPassengers() throws Exception {

  }

  @Test
  public void testChangePassengerCreated() throws Exception {

  }

  @Test
  public void testChangeMoreEnterProbability() throws Exception {

  }

  @Test
  public void testChangeMotorOutput() throws Exception {

  }

  @Test
  public void testChangeForceBreak() throws Exception {

  }

  @Test
  public void testChangeCabinLimitWeight() throws Exception {

  }
}