__author__ = 'Byoungwoo'
import __init__
import random
this={
    "deltaTime": None,
    "validate" : False,
    "floor":None,
    "fireFighter":None
}

def setParameter(key,val):
    if key in  this : this[key]=val


def trigger():
    #global variables
    global defaultCircumstance
    engine = __init__.Fecs.getApplicationContext().getBean("engine")
    passengerMaker = __init__.Fecs.getApplicationContext().getBean("passengerMaker")
    out=__init__.System.out
    FloorType=__init__.FloorType
    floors = engine.getFloors()
    if not this["validate"] :
        if this["floor"] is None:
            rand = random.randint(0,9)
            if rand==0 : rand = -1
            else :rand+=1
            this["floor"] = floors.get(FloorType.valueOf(rand))
        out.println("fire circumstance started with floor "+str(this["floor"].getNum()))
        this["floor"].killPassengers()
        fireFighter=__init__.Passenger(1,this["floor"].getNum())
        out.println(fireFighter)
        floors.get(FloorType.FIRST).getPassengers().add(fireFighter)
        this["fireFighter"]=fireFighter
        this["validate"] = True
    else :
        out.println("fire fighter going")
        for cabin in engine.getCabins().values():
            fireFighter = this["fireFighter"]
            out.println("fire fighter going")
            if cabin.getPosition()==this["floor"].getPosition() and cabin.getPassengers().contains(fireFighter) :
                out.println("firefighter arrived")
                cabin.getPassengers().remove(fireFighter)
                this["floor"]=None
                engine.setState(__init__.ICircumstance.STATE_DEFAULT << 1 | (engine.getState() & 1))
                __init__.Fecs.getApplicationContext().getBean("userInterface").endFail()
                return
        defaultCircumstance.setParameter("deltaTime",this["deltaTime"]).trigger()

    pass