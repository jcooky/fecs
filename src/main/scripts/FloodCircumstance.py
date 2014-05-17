__author__ = 'Byoungwoo'

this={}

def setParameter(key,val):
    if key in  this : this[key]=val

def trigger():
    #global variables
    if 'passengerMaker' or 'engine' not in globals() : return
    passengerMaker = globals()["passengerMaker"]
    engine = globals()["engine"]
    pass