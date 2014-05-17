__author__ = 'Byoungwoo'

def setParameter(key,val):
    __builtins__[key]=val

def trigger():
    #global variables
    if 'passengerMaker' or 'engine' not in globals() : return
    passengerMaker = globals()["passengerMaker"]
    engine = globals()["engine"]
    pass