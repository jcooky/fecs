__author__ = 'Byoungwoo'

import __init__

this={
    "validate": False,
    "floor":None,
    "cabinWait":1000
}

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    #global variables
    global passengerMaker, engine
    out = __init__.out
    passengerMaker.makePassenger()
    cabins = engine.getCabins()
    floors = engine.getFloors()
    floors.get(__init__.FloorTypes.FIRST)
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