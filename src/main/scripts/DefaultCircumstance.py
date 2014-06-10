__author__ = 'Byoungwoo'

import __init__

MAKE_WAIT_SEC=1.0 # 1sec wait to make
doorWait=1.0,1.0  ## 1sec wait to door open/close
this={
  # "currentTime": None,
  "makeWait": MAKE_WAIT_SEC,
  "deltaTime": None,
  "validate": False,
  "noPassengerMode" : False,
  "doorWait":doorWait
}
logger = __init__.LoggerFactory.getLogger("fecs.DefaultCircumstance")

def setParameter(key,val):
  if key in  this : this[key]=val

def trigger():
  #global variables
  global passengerMaker, crashCircumstance#, engine
  engine = __init__.Fecs.getApplicationContext().getBean("engine")
  #local variables
  out = __init__.System.out
  cabinsController = __init__.CabinsController
  FloorType = __init__.FloorType
  CabinType = __init__.CabinType
  #local function
  def cabinGo(cabin):
    # out.println("cabin goes")
    que=cabin.getQueue()
    # out.println(cabin.getPassengers())
    for p in  cabin.getPassengers():
      # out.println(p)
      # out.println(p.getDest())
      destFloor = engine.getFloors().get(FloorType.valueOf(p.getDest()))
      # out.println(destFloor)
      if not que.contains(destFloor):
        que.add(destFloor)
    cabin.move()

  def TakeIn(cabin,passengers):
    for p in passengers.toArray():
      passengers.remove(p)
      cabin.getPassengers().add(p)
      p.setState(p.State.RIDING)

  # out.println("default trigger started")
  if not this["noPassengerMode"]:
    this["makeWait"] -= this["deltaTime"]
    if this["makeWait"] < 0 :
      passengerMaker.makePassenger()
      this["makeWait"]=MAKE_WAIT_SEC

  cabins = engine.getCabins()
  floors = engine.getFloors()

  # clear non-waiting passengers in all floor
  for floor in floors.values():
    floor.removeUsingFilterNoWait()

  # for cabin in cabins.values() :
  #   if not cabin.isOn(): cabin.enable()
  #   # if cabin.getTarget() is None: continue
  #   # out.println("updating cabin"+str(cabin)+" by "+str(this["deltaTime"]))
  #   engine.updateCabin(cabin,this["deltaTime"])
  #   # out.println(cabins)

  firstFloor = floors.get(FloorType.FIRST)
  firstFloorPassengers = firstFloor.getPassengers()
  # out.println(firstFloorPassengers)
  if firstFloorPassengers.size()>0 :
    arrivedCabin=None
    arrivedCabinNo=0
    for i,c in enumerate(cabins.values()):
      if c.getPosition()==firstFloor.getPosition() and c.getState()==c.State.STOP : #and c.getState()==c.State.STOP :
        arrivedCabin = c
        arrivedCabinNo = i
    # out.println("cabin["+str(arrivedCabin)+"] arrived on firstfloor")
    if arrivedCabin is not None:
      # out.println("cabin arrived")
      # out.println(str(arrivedCabinNo)+" cabin : ")
      # out.println(str(this["doorWait"][arrivedCabinNo]))
      # this["doorWait"][arrivedCabinNo]-=this["deltaTime"]
      # out.println(this["doorWait"][arrivedCabinNo])
      # if this["doorWait"][arrivedCabinNo]<=0 :
      # out.println(arrivedCabin.getPassengers().size())
      # out.println(engine.getCabinLimitPeople())
      if firstFloorPassengers.size()+arrivedCabin.getPassengers().size() > engine.getCabinLimitPeople() :
        # out.println("too many passengers. dice roll")
        if __init__.Math.random() < engine.getMoreEnterProbability() :
          out.println("death dice")
          TakeIn(arrivedCabin,firstFloorPassengers)
          crashCircumstance.setParameter('cabin',arrivedCabin)
          engine.setCircumstanceState(__init__.CircumstanceType.CRASH.state())
          ui=__init__.Fecs.getApplicationContext().getBean("userInterface")
          ui.startFail()

        else:
          out.println("just go dice")
          limitedPassengers = engine.getCabinLimitPeople()-arrivedCabin.getPassengers().size()
          i=0
          for p in firstFloorPassengers.toArray():
            if i<limitedPassengers:
              firstFloorPassengers.remove(p)
              arrivedCabin.getPassengers().add(p)
            i+=1
          # out.println(arrivedCabin.getPassengers().size())
          cabinGo(arrivedCabin)
      else:
        # out.println("adequate people awaits.")
        # out.println(firstFloorPassengers)
        TakeIn(arrivedCabin,firstFloorPassengers)
        cabinGo(arrivedCabin)
    else :
      # out.println("cabin not arrived. ordered to come")
      left = cabins.get(CabinType.LEFT)
      right = cabins.get(CabinType.RIGHT)
      # engine.getPushedFloorSet().add(firstFloor)
      # out.println(engine.getPushedFloorSet())
      cabinsController.control(left,right,firstFloor)