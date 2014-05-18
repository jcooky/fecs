__author__ = 'Byoungwoo'
import __init__
from fecs import Fecs
logger = __init__.LoggerFactory.getLogger("fecs.EarthQuakeCircumstance")
this={
    "startTime": None,
    "currentTime": None,
    "killTime": 1000
}

def setParameter(key,val):
    if key in  this : this[key]=val


def trigger():
    logger.debug("CALL trigger")
    engine = Fecs.getApplicationContext().getBean("engine")
    passengerMaker = Fecs.getApplicationContext().getBean("passengerMaker")
    ui = Fecs.getApplicationContext().getBean("userInterface")

    currentTime = this["currentTime"]
    startTime = this["startTime"]

    for cabin in engine.getCabins().values():
        cabin.disable()

    if currentTime - startTime > this["killTime"]:
        passengerMaker.killRandom()
        this["killTime"] += 1000

    if currentTime - startTime > 60000:
        logger.debug("ended")
        ui.endFail()
        engine.setState(__init__.ICircumstance.STATE_DEFAULT << 1 | (engine.getState() & 1))
        __init__.System.out.println(engine.getState())
        for cabin in engine.getCabins().values():
            cabin.enable()

