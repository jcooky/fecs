__author__ = 'Byoungwoo'

from fecs.interfaces import IFecs

def setParameter(key,val):
    globals()[key]=val

def trigger():
    passengerMaker = vars()["passengerMaker"][0]
    passengerMaker.makePassenger()
