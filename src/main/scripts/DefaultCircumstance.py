__author__ = 'Byoungwoo'

import __init__

this={
    "validate": False,
    "floor":None
}

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    #global variables
    global engine, passengerMaker,out
    if 'passengerMaker' or 'engine' not in globals() : return

    out.println("triggered")

    #module variables
    if 'cabinWait' not in this : this['cabinWait'] = [1000,1000]
    # __builtins__[""]

    passengerMaker.makePassenger()
    cabins = engine.getCabins()
    for i, cabin in enumerate(cabins):
        if not cabin.getQueue().isEmpty(): return
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