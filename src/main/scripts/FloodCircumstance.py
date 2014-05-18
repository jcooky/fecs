__author__ = 'Byoungwoo'
import __init__

this={
    "currentTime": 0,
    "startTime": 0,
    "state": "NONE",
}
q = {}
logger = __init__.LoggerFactory.getLogger("fecs.FloodCircumstance")
FloorType = __init__.FloorType

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    engine = __init__.Fecs.getApplicationContext().getBean("engine")
    ui = __init__.Fecs.getApplicationContext().getBean("userInterface")

    startTime = this["startTime"]
    currentTime = this["currentTime"]

    if this["state"] == "NONE":
        q["LEFT"] = []
        q["RIGHT"] = []
        for cabinType in engine.getCabins().keySet():
            cabin = engine.getCabins().get(cabinType)
            q[cabinType.toString()].append(cabin.getTarget())
            while not cabin.getQueue().isEmpty():
                q[cabinType.toString()].append(cabin.getQueue().poll())
            q[cabinType.toString()].reverse()
            cabin.stop()
            cabin.move(engine.getFloors().get(FloorType.TENTH))
        engine.getFloors().get(FloorType.FIRST).getPassengers().clear()
        this["state"] = "MOVING"
    elif this["state"] == "MOVING":
        stopped = True
        for cabin in engine.getCabins().values():
            if cabin.getState() != cabin.State.STOP:
                stopped = False
        if stopped:
            this["state"] = "STOP"
    elif this["state"] == "STOP":
        if currentTime - startTime > 60000:
            for cabinType in engine.getCabins().keySet():
                cabin = engine.getCabins().get(cabinType)
                while len(q[cabinType.toString()]) != 0:
                    cabin.move(q[cabinType.toString()].pop())
            this["state"] = "NONE"
            engine.setState(__init__.ICircumstance.STATE_DEFAULT << 1 | (engine.getState() & 1))
            ui.endFail()


