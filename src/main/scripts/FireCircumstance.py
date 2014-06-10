__author__ = 'Byoungwoo'
import __init__

this={
    "deltaTime": None,
    "validate" : False,
    "floor":None,
    "fireFighter":None,
    "leftTime" : 2000
}

logger = __init__.LoggerFactory.getLogger("fecs.FireCircumstance")

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
    CabinType = __init__.CabinType
    floors = engine.getFloors()
    if not this["validate"] :
        out.println("fire circumstance started with floor "+str(this["floor"].getNum()))
        this["floor"].killPassengers()
        fireFighter=passengerMaker.makeFireFighter(this["floor"].getNum())
        out.println("1st floor passengers evacuated")
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
          logger.debug("fire fighter going")
        else:
          logger.debug("firefighter arrived; leftTime: {}", this["leftTime"])
          if this["leftTime"] >0:
            this["leftTime"]-=this["deltaTime"]
            cabin.setTarget(this["floor"])
          else:
            #reset internal state and turn back to normal circumstance
            this["floor"]=None
            defaultCircumstance.setParameter("noPassengerMode",False)
            engine.setCircumstanceState(__init__.CircumstanceType.DEFAULT.state())
            __init__.Fecs.getApplicationContext().getBean("userInterface").endFail()
            return
        #else : out.println("fire fighter waiting")
    pass