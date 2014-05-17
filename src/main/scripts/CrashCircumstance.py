__author__ = 'Byoungwoo'

from java.lang import System
from fecs.model import FloorType
from org.slf4j import LoggerFactory
from fecs import Fecs

this={
    "cabin": None,
    "firstCalled": False
}
logger = LoggerFactory.getLogger("fecs.CrashCircumstance")

def setParameter(key,val):
    if key in  this : this[key]=val
    logger.debug("CALL setParameter")

def trigger():
    logger.debug("CALL trigger")

    engine = Fecs.getApplicationContext().getBean("engine")

    cabin = this["cabin"]

    if not this["firstCalled"]:
        cabin.disable()
        this["firstCalled"] = True
        this["lastUpdateTime"] = this["firstTime"] = System.currentTimeMillis()
        logger.trace("CALL first trigger")
    else:
        logger.trace("TRIGGER progress")

        curtime = System.currentTimeMillis()
        deltaTime = curtime - this["lastUpdateTime"]
        cabin.setPosition(cabin.getPosition() + (0.5 * engine.getGravity() * deltaTime * deltaTime));

        if cabin.getPosition() >= engine.getFloors().get(FloorType.UNDER_FIRST).getPosition():
            cabin.setPosition(engine.getFloors().get(FloorType.UNDER_FIRST).getPosition())
            force = (engine.mass(cabin) * engine.getGravity()) - engine.getForceBreak()
            if force * (curtime - this["firstTime"]) > 8333.33:
                cabin.killPassengers()
            cabin.enable()
            this["firstCalled"] = False

        this["lastUpdateTime"] = curtime