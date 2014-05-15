__author__ = 'Byoungwoo'
from fecs import Circumstance #,Engine

class DefualtCircumstance(Circumstance):
    def setParameter(self,key,val):
        return
    def trigger(self):

        return True

class CrashCircumstance(Circumstance):
    def setParameter(self,key,val):
        if key=='cabin' : self.cabin=val;
        return
    def trigger(self):

        return True

class FireCircumstance(Circumstance):
    def setParameter(self,key,val):
        return
    def trigger(self):
        return True

class FloodCircumstance(Circumstance):
    def setParameter(self,key,val):
        return
    def trigger(self):
        return True

class EarthQuakeCircumstance(Circumstance):
    def setParameter(self,key,val):
        return
    def trigger(self):
        return True