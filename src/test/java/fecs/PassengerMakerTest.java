package fecs;

import fecs.admin.support.AbstractSpringBasedTestSupport;
import fecs.model.Floor;
import fecs.model.FloorType;
import fecs.model.Passenger;
import fecs.physics.Engine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PassengerMakerTest extends AbstractSpringBasedTestSupport {

  @Mock
  private Engine engine;

  private Map<FloorType, Floor> floors = new HashMap<>();

  @Autowired
  @InjectMocks
  private PassengerMaker passengerMaker;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    for (FloorType type : FloorType.values()) floors.put(type, mock(Floor.class));
  }

  @Test
  public void testMakePassenger() throws Exception {
    // given
    passengerMaker.setHowMany(10);
    passengerMaker.setMax(30);

    when(engine.getFloors()).thenReturn(floors);
    passengerMaker.makePassenger();

    verify(floors.get(FloorType.FIRST), times(10)).add(any(Passenger.class));
  }
}