__author__ = 'Byoungwoo'

import __init__

this={
    "cabin": None,
    "firstCalled": False
}
logger = __init__.LoggerFactory.getLogger("fecs.CrashCircumstance")

def setParameter(key,val):
    if key in  this : this[key]=val
    logger.debug("CALL setParameter")

def trigger():
    logger.debug("CALL trigger")
    System = __init__.System
    engine = __init__.Fecs.getApplicationContext().getBean("engine")

    engine = __init__.DefaultCircumstance.engine

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
            engine.setState(engine.getState() & 0x00000001)

        this["lastUpdateTime"] = curtime