package fecs.interfaces;

import fecs.Engine;
import fecs.PassengerMaker;

/**
 * Created by Byoungwoo on 2014-05-16.
 */
public interface IFecs {
  static IPassengerMaker passengerMaker = new PassengerMaker();
  static IEngine engine = new Engine();
}
