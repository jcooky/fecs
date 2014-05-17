__author__ = 'Byoungwoo'

import __init__

this={
    "cabin": None,
    "deltaTime": None,
    "firstCalled": False,
    "validate": False
}
logger = __init__.LoggerFactory.getLogger("fecs.CrashCircumstance")

def setParameter(key, val):
    if key in this: this[key] = val

def trigger():
    logger.debug("CALL trigger")
    System = __init__.System
    engine = __init__.Fecs.getApplicationContext().getBean("engine")
    FloorType = __init__.FloorType


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
                __init__.Fecs.getApplicationContext().getBean("userInterface").endFail()
    else:
        this["validate"] = False
        cabins = engine.getCabins()
        for cabin in cabins.values():
            if cabin.getPassengers().size() * engine.getPassengerWeight() + engine.getCabinWeight() >= engine.getCabinLimitWeight():
                this["cabin"] = cabin
                engine.setState((__init__.ICircumstance.STATE_CRASH << 1) | (engine.getState() & 1))
                trigger()
                break
