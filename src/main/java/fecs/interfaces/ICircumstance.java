package fecs.interfaces;

/**
 * Created by Byoungwoo on 2014-05-14.
 */
public interface ICircumstance {

  void trigger();
  fecs.Circumstance setParameter(String key,Object val);
}
