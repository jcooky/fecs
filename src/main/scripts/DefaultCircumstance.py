__author__ = 'Byoungwoo'

def setParameter(key,val):
    __builtins__[key]=val

def trigger():
    if 'passengerMaker' or 'engine' not in globals() : return()
    passengerMaker = globals()["passengerMaker"]
    engine = globals()["engine"]

    passengerMaker.makePassenger()
    cabins = engine.getCabins()
    for cabin in cabins:
        # if cabin.getQueue().isEmpty() :
        #     __builtins__['cabin wait'+__iter__]
        #     cabin.set
        #     return
        return
    #if(engine.cabins.get(CabinType.LEFT))