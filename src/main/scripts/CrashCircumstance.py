__author__ = 'Byoungwoo'

from fecs.model import FloorType
from org.slf4j import LoggerFactory
from fecs import Fecs

this = {
    "cabin": None,
    "deltaTime": None,
    "firstCalled": False
}
logger = LoggerFactory.getLogger("fecs.CrashCircumstance")

def setParameter(key, val):
    if key in this: this[key] = val
    logger.debug("CALL setParameter")

def trigger():
    logger.debug("CALL trigger")

    engine = Fecs.getApplicationContext().getBean("engine")

    cabin = this["cabin"]
    deltaTime = this["deltaTime"]

    if not this["firstCalled"]:
        cabin.disable()
        this["firstCalled"] = True
        this["totalTime"] = 0.0
        logger.trace("CALL first trigger")
    else:
        this["totalTime"] += deltaTime
        logger.trace("TRIGGER progress")
        engine.updateCabin(cabin, engine.getGravity(), deltaTime);
        logger.trace("cabin.position: {}", cabin.getPosition())

        if cabin.getPosition() >= engine.getFloors().get(FloorType.UNDER_FIRST).getPosition():
            cabin.setPosition(engine.getFloors().get(FloorType.UNDER_FIRST).getPosition())
            force = (engine.mass(cabin) * engine.getGravity()) - engine.getForceBreak()
            if force * this["totalTime"] > 8333.33:
                cabin.killPassengers()

            cabin.enable()
            this["firstCalled"] = False
            engine.setState(engine.getState() & 0x00000001)

