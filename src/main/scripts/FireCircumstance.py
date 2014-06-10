__author__ = 'Byoungwoo'
import __init__

this={
    "deltaTime": None,
    "validate" : False,
    "floor":None,
    "fireFighter":None,
    "leftTime" : 2000
}

def setParameter(key,val):
  if key in  this : this[key]=val

def getParameter(key):
  if key in this : return this[key]

def trigger():
    #global variables
    global defaultCircumstance
    engine = __init__.Fecs.getApplicationContext().getBean("engine")
    out=__init__.System.out
    FloorType=__init__.FloorType
    CabinType = __init__.CabinType
    floors = engine.getFloors()
    if not this["validate"] :
        out.println("fire circumstance started with floor "+str(this["floor"].getNum()))
        this["floor"].killPassengers()
        fireFighter=__init__.Passenger(1,this["floor"].getNum())
        firstFloorPassengers=floors.get(FloorType.FIRST).getPassengers()
        firstFloorPassengers.clear()
        out.println("1st floor passengers evacuated")
        firstFloorPassengers.add(fireFighter)
        out.println("fire fighter arrived the building")
        defaultCircumstance.setParameter("noPassengerMode",True)
        this["fireFighter"]=fireFighter
        this["validate"] = True
    else :
        fireFighter = this["fireFighter"]
        ffstate=fireFighter.getState()
        defaultCircumstance.setParameter("deltaTime",this["deltaTime"]).trigger()
        cabins = engine.getCabins()
        cabin=None
        out.println(fireFighter)
        for cab in cabins.values():
          if cab.getPassengers().contains(fireFighter) :
            cabin=cab
        if ffstate==fireFighter.State.WAIT:
          left = cabins.get(CabinType.LEFT)
          right = cabins.get(CabinType.RIGHT)
          limit = engine.getCabinLimitPeople()
          firstFloor = floors.get(FloorType.FIRST)
          leftDist=abs(left.getPosition()-firstFloor.getPosition())
          rightDist =abs(right.getPosition()-firstFloor.getPosition())
          if  leftDist<=rightDist :
            if left.getPassengers().size()<limit:
              left.setTarget(firstFloor)
          else :
            if right.getPassengers().size()<limit:
              right.setTarget(firstFloor)
        elif ffstate==fireFighter.State.RIDING:
          cabin.setTarget(this["floor"])
          out.println("fire fighter going")
        else:
          out.println("firefighter arrived")
          if this["leftTime"] >0:
            this["leftTime"]-=this["deltaTime"]
            cabin.setTarget(this["floor"])
          else:
            #reset internal state and turn back to normal circumstance
            this["floor"]=None
            defaultCircumstance.setParameter("noPassengerMode",False)
            engine.setCircumstanceState(__init__.ICircumstance.STATE_DEFAULT)
            __init__.Fecs.getApplicationContext().getBean("userInterface").endFail()
            return
        #else : out.println("fire fighter waiting")
    pass