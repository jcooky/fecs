__author__ = 'Byoungwoo'
import __init__

this={
    "deltaTime": None,
    "validate" : False,
    "floor":None,
    "fireFighter":None
}

def setParameter(key,val):
  if key in  this : this[key]=val

def getParameter(key):
  if key in this : return this[key]

def trigger():
    #global variables
    global defaultCircumstance
    engine = __init__.Fecs.getApplicationContext().getBean("engine")
    passengerMaker = __init__.Fecs.getApplicationContext().getBean("passengerMaker")
    out=__init__.System.out
    FloorType=__init__.FloorType
    floors = engine.getFloors()
    if not this["validate"] :
        out.println("fire circumstance started with floor "+str(this["floor"].getNum()))
        this["floor"].killPassengers()
        fireFighter=__init__.Passenger(1,this["floor"].getNum())
        # out.println(fireFighter)
        firstFloorPassengers=floors.get(FloorType.FIRST).getPassengers()
        firstFloorPassengers.clear()
        out.println("1st floor passengers evacuated")
        firstFloorPassengers.add(fireFighter)
        out.println("fire fighter arrived the building")
        defaultCircumstance.setParameter("noPassengerMode",True)
        this["fireFighter"]=fireFighter
        this["validate"] = True
    else :
        defaultCircumstance.setParameter("deltaTime",this["deltaTime"]).trigger()
        fireFighter = this["fireFighter"]
        if fireFighter.getState()==fireFighter.State.RIDING:
          out.println("fire fighter going")
        elif fireFighter.getState()==fireFighter.State.NO_WAIT:
          out.println("firefighter arrived")
          #reset internal state and turn back to normal circumstance
          this["floor"]=None
          defaultCircumstance.setParameter("noPassengerMode",False)
          engine.setCircumstanceState(__init__.ICircumstance.STATE_DEFAULT)
          __init__.Fecs.getApplicationContext().getBean("userInterface").endFail()
          return
        #else : out.println("fire fighter waiting")
    pass