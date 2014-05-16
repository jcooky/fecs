__author__ = 'Byoungwoo'

from fecs.interfaces import IFecs

def setParameter(key,val):
    vars()[key]=val

def trigger():
    vars()[time]-=1
    pass