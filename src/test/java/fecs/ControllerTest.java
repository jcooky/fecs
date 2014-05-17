package fecs;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.interfaces.ICircumstance;
import fecs.interfaces.IEngine;
import fecs.simulator.Engine;
import fecs.simulator.PassengerMaker;
import fecs.ui.Controller;
import fecs.ui.UserInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

import static org.mockito.Mockito.*;

public class ControllerTest extends AbstractSpringBasedTestSupport {
  @Autowired
  @Spy
  @InjectMocks
  private Controller controller;

  @Mock
  private Engine engine;

  @Mock
  private UserInterface ui;

  @Mock
  private PassengerMaker passengerMaker;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        return null;
      }
    }).when(controller).displayError(anyString());
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
    // when
    when(engine.getGravity()).thenReturn(0.0);
    when(ui.getGravity()).thenReturn(mock(JTextField.class));
    controller.changeGravity("9.8");

    // then
    verify(engine).setGravity(Engine.earthGravity);
    verify(ui.getGravity()).setText(Engine.earthGravity.toString());
  }

  @Test
  public void testChangeGravityOver50() throws Exception {
    // when
    when(engine.getGravity()).thenReturn(0.0);
    when(ui.getGravity()).thenReturn(mock(JTextField.class));
    controller.changeGravity("51.0");

    // then
    verify(engine).setGravity(50.0);
    verify(ui.getGravity()).setText("50.0");
  }

  @Test
  public void testChangeGravityUnder0() throws Exception {
    // when
    when(engine.getGravity()).thenReturn(0.0);
    when(ui.getGravity()).thenReturn(mock(JTextField.class));
    controller.changeGravity("-1.0");

    // then
    verify(engine).setGravity(0.0);
    verify(ui.getGravity()).setText("0.0");
  }

  @Test
  public void testChangeCabinLimitPeople() throws Exception {
    // when
    when(engine.getCabinWeight()).thenReturn(0.0);
    when(engine.getCabinLimitWeight()).thenReturn(0.0);
    when(engine.getPassengerWeight()).thenReturn(0.0);
    controller.changeCabinLimitPeople("10");

    // then
    verify(engine).setCabinLimitPeople(10);
  }

  @Test
  public void testChangeCabinLimitPeopleWithOverLimit() throws Exception {
    // when
    when(engine.getCabinWeight()).thenReturn(0.0);
    when(engine.getCabinLimitWeight()).thenReturn(4.0);
    when(engine.getPassengerWeight()).thenReturn(1.0);
    when(ui.getCabinLimitPeople()).thenReturn(mock(JTextField.class));
    controller.changeCabinLimitPeople("5");

    // then
    verify(engine).setCabinLimitPeople(4);
    verify(ui.getCabinLimitPeople()).setText("4");
  }

  @Test
  public void testChangeCabinWeight() throws Exception {
    // when
    controller.changeCabinWeight("10.0");

    // then
    verify(engine).setCabinWeight(10.0);
  }

  @Test
  public void testChangePassengerWeight() throws Exception {
    // when
    controller.changePassengerWeight("30.0");

    // then
    verify(engine).setPassengerWeight(30.0);
  }

  @Test
  public void testChangeTotalNumPassengers() throws Exception {
    // when
    when(passengerMaker.getHowMany()).thenReturn(10);
    controller.changeTotalNumPassengers("20");

    // then
    verify(passengerMaker).setMax(20);
  }

  @Test
  public void testChangeTotalNumPassengersWithOver() throws Exception {
    // when
    when(passengerMaker.getHowMany()).thenReturn(20);
    when(passengerMaker.getMax()).thenReturn(10);
    controller.changeTotalNumPassengers("10");

    // then
    verify(passengerMaker).setMax(10);
    verify(controller).changePassengerCreated("10");
    verify(controller).displayError(anyString());
  }

  @Test
  public void testChangePassengerCreated() throws Exception {
    // when
    when(passengerMaker.getMax()).thenReturn(20);
    controller.changePassengerCreated("10");

    // then
    verify(passengerMaker).setHowMany(10);
  }

  @Test
  public void testChangePassengerCreatedWithOver() throws Exception {
    // when
    when(passengerMaker.getHowMany()).thenReturn(20);
    when(passengerMaker.getMax()).thenReturn(10);
    controller.changePassengerCreated("20");

    // then
    verify(passengerMaker).setHowMany(20);
    verify(controller).changeTotalNumPassengers("20");
    verify(controller).displayError(anyString());
  }

  @Test
  public void testChangeMoreEnterProbability() throws Exception {
    // when
    controller.changeMoreEnterProbability("0.22");

    // then
    verify(engine).setMoreEnterProbability(0.22);
  }

  @Test
  public void testChangeMotorOutput() throws Exception {
    // when
    controller.changeMotorOutput("100.0");

    // then
    verify(engine).setMotorOutput(100.0);
  }

  @Test
  public void testChangeForceBreak() throws Exception {
    // when
    controller.changeForceBreak("1000.0");

    // then
    verify(engine).setForceBreak(1000.0);
  }

  @Test
  public void testChangeCabinLimitWeight() throws Exception {
    // when
    when(engine.getCabinWeight()).thenReturn(0.0);
    when(engine.getCabinLimitWeight()).thenReturn(0.0);
    when(engine.getPassengerWeight()).thenReturn(0.0);
    controller.changeCabinLimitWeight("4.0");

    // then
    verify(engine).setCabinLimitWeight(4.0);
  }

  @Test
  public void testChangeCabinLimitWeightWithOver() throws Exception {
    // when
    when(engine.getCabinWeight()).thenReturn(5.0);
    when(engine.getCabinLimitWeight()).thenReturn(0.0);
    when(engine.getPassengerWeight()).thenReturn(0.0);
    when(ui.getCabinLimitWeight()).thenReturn(mock(JTextField.class));
    controller.changeCabinLimitWeight("1.0");

    // then
    verify(engine).setCabinLimitWeight(5.0);
    verify(ui.getCabinLimitWeight()).setText("5.0");
  }
}