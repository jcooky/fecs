__author__ = 'Byoungwoo'

import __init__

MAKE_WAIT_SEC=1.0 # 1sec wait to make
doorWait=1.0,1.0  ## 1sec wait to door open/close
this={
    # "currentTime": None,
    "makeWait": MAKE_WAIT_SEC,
    "deltaTime": None,
    "validate": False,
    "doorWait":doorWait
}

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    #global variables
    global passengerMaker, engine, crash

    #local variables
    out = __init__.System.out
    cabinsController = __init__.CabinsController
    FloorType = __init__.FloorType
    CabinType = __init__.CabinType
    #local function
    def cabinGo(cabin):
        out.println("cabin goes")
        que=cabin.getQueue()
        # out.println(cabin.getPassengers())
        for p in  cabin.getPassengers():
            # out.println(p)
            # out.println(p.getDest())
            destFloor = engine.getFloors().get(FloorType.valueOf(p.getDest()))
            # out.println(destFloor)
            if not que.contains(destFloor):
                que.add(destFloor)
        cabin.enable()
        cabin.move()

    def TakeIn(cabin,passengers):
        for p in passengers.toArray():
            passengers.remove(p)
            cabin.getPassengers().add(p)

    this["makeWait"] -= this["deltaTime"]
    # out.println("default trigger started with " + str(this["makeWait"]))
    if this["makeWait"] < 0 :
        passengerMaker.makePassenger()
        this["makeWait"]=MAKE_WAIT_SEC

    cabins = engine.getCabins()

    for cabin in cabins.values() :
        if not cabin.isOn(): cabin.enable()


    floors = engine.getFloors()

    for floor in floors.values():
        for cabin in cabins.values():
            if floor.getPosition() == cabin.getPosition():
                # if cabin.getTaget() is not None:
                #     out.println(cabin.getTarget())
                #     if cabin.getTarget()==floor:
                # out.println("cabin("+str(cabin)+") arrived on floor("+str(floor.getNum())+")")
                for p in cabin.getPassengers().toArray():
                    if p.getDest()==floor.getNum() :
                        # out.println(str(p)+'wants to take off')
                        cabin.getPassengers().remove(p)
                        passengerMaker.setNow(passengerMaker.getNow()-1)

    firstFloor = floors.get(FloorType.FIRST)
    firstFloorPassengers = firstFloor.getPassengers()

    if firstFloorPassengers.size()>0 :
        arrivedCabin=None
        arrivedCabinNo=0
        for i,c in enumerate(cabins.values()):
            if c.getPosition()==firstFloor.getPosition() and c.getState()==c.State.STOP : #and c.getState()==c.State.STOP :
                arrivedCabin = c
                arrivedCabinNo = i
        # out.println("cabin["+str(arrivedCabin)+"] arrived on firstfloor")
        if arrivedCabin:
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
                if 1-__init__.Math.random() < engine.getMoreEnterProbability() :
                    out.println("death dice")
                    TakeIn(arrivedCabin,firstFloorPassengers)
                    crash.setParameter('validate',False)\
                        .setParameter('cabin',arrivedCabin)\
                        .trigger()
                    engine.setState(__init__.ICircumstance.STATE_CRASH << 1 | (engine.getState() & 1))
                    __init__.Fecs.getApplicationContext().getBean("userInterface").startFail()

                else:
                    out.println("just go dice")
                    limitedPassengers = engine.getCabinLimitPeople()-arrivedCabin.getPassengers().size()
                    i=0
                    for p in firstFloorPassengers.toArray():
                        if i<limitedPassengers:
                            firstFloorPassengers.remove(p)
                            arrivedCabin.getPassengers().add(p)
                        i+=1
                    out.println(arrivedCabin.getPassengers().size())
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
            cabinsController.control(left,right,firstFloor)


    # for i, cabin in enumerate(cabins):
    #     if not cabin.getQueue().isEmpty(): return
    # if this["cabinWait"][i]<=0 :
    #     cabin.enable()
    #     this["cabinWait"][i]=1000
    # else :
    #     cabin.disable()
    #     this["cabinWait"][i]-=1
    # return
    #     __builtins__['cabin wait'+__iter__]
    #     cabin.set
    #     return
    # return
    #if(engine.cabins.get(CabinType.LEFT))