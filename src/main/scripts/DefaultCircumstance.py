__author__ = 'Byoungwoo'

from fecs.interfaces import IFecs

def setParameter(key,val):
    vars()[key]=val

def trigger():
    IFecs.passengerMaker.update()