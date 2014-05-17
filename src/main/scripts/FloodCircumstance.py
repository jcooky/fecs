__author__ = 'Byoungwoo'

from fecs import Fecs
from fecs.model import FloorType
from fecs.model import Vector
from java.lang import Math
from fecs.simulator import Cabin
from java.util import LinkedList
from org.slf4j import LoggerFactory

this={
    "currentTime": 0,
    "startTime": 0,
    "state": "NONE",
}
q = {}
logger = LoggerFactory.getLogger("fecs.FloodCircumstance")

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    engine = Fecs.getApplicationContext().getBean("engine")
    ui = Fecs.getApplicationContext().getBean("userInterface")

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
            if cabin.getState() != Cabin.State.STOP:
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
            engine.setState(engine.getState() & 1)
            ui.endFail()


