package fecs;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

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