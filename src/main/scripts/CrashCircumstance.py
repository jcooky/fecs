__author__ = 'Byoungwoo'

from fecs.model import FloorType
from org.slf4j import LoggerFactory
from fecs import Fecs
from fecs.interfaces import ICircumstance

this = {
    "cabin": None,
    "deltaTime": None,
    "firstCalled": False,
    "validate": False
}
logger = LoggerFactory.getLogger("fecs.CrashCircumstance")

def setParameter(key, val):
    if key in this: this[key] = val

def trigger():

    engine = Fecs.getApplicationContext().getBean("engine")

    validate = this["validate"]
    deltaTime = this["deltaTime"]

    if not validate:
        cabin = this["cabin"]
        if not this["firstCalled"]:
            cabin.disable()
            this["firstCalled"] = True
            this["totalTime"] = 0.0
        else:
            this["totalTime"] += deltaTime
            logger.trace("TRIGGER progress")
            engine.updateCabin(cabin, engine.getGravity(), deltaTime);

            if cabin.getPosition() >= engine.getFloors().get(FloorType.UNDER_FIRST).getPosition():
                cabin.setPosition(engine.getFloors().get(FloorType.UNDER_FIRST).getPosition())

                force = (engine.mass(cabin) * engine.getGravity()) - engine.getForceBreak()
                logger.debug("force: {}", force)
                if force * this["totalTime"] > 8333.33:
                    cabin.killPassengers()

                cabin.stop()
                cabin.enable()
                this["firstCalled"] = False
                engine.setState(engine.getState() & 0x00000001)
                Fecs.getApplicationContext().getBean("userInterface").endFail()
    else:
        this["validate"] = False
        cabins = engine.getCabins()
        for cabin in cabins.values():
            if cabin.getPassengers().size() * engine.getPassengerWeight() + engine.getCabinWeight() >= engine.getCabinLimitWeight():
                this["cabin"] = cabin
                engine.setState((ICircumstance.STATE_CRASH << 1) | (engine.getState() & 1))
                trigger()
                break

