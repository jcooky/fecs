__author__ = 'Byoungwoo'

#global imports

from org.slf4j import LoggerFactory
from fecs import Fecs
from fecs import Fecs, CabinsController
from fecs.model import CabinType, FloorType, Vector
from fecs.interfaces import ICircumstance

from java.lang import System
from System import out

engine = Fecs.getApplicationContext().getBean("engine")
passengerMaker = Fecs.getApplicationContext().getBean("passengerMaker")

out.println('jython global variables imported')