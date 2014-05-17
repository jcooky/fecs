__author__ = 'Byoungwoo'

from fecs import Fecs

this={
    "startTime": None,
    "currentTime": None,
    "killTime": 1000
}

def setParameter(key,val):
    if key in  this : this[key]=val


def trigger():
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
        ui.endFail()
        engine.setState(engine.getState() & 1)
        for cabin in engine.getCabins().values():
            cabin.enable()

