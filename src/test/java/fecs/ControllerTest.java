package fecs;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.interfaces.ICircumstance;
import fecs.interfaces.IEngine;
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
    // when
    when(engine.getState()).thenReturn(0);
    controller.startSimulation();

    // then
    verify(engine).setState(IEngine.STATE_START);
  }

  @Test
  public void testStopSimulation() throws Exception {
    // when
    when(engine.getState()).thenReturn(1);
    controller.stopSimulation();

    // then
    verify(engine).setState(IEngine.STATE_STOP);
  }

  @Test
  public void testStopSimulationWithOthers() throws Exception {
    // when
    when(engine.getState()).thenReturn((ICircumstance.STATE_CRASH << 1) | Engine.STATE_START);
    controller.stopSimulation();

    verify(engine).setState((ICircumstance.STATE_CRASH << 1));
  }

  @Test
  public void testTriggerFail() throws Exception {
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