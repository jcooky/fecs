__author__ = 'Byoungwoo'

#global imports

from org.slf4j import LoggerFactory
from fecs import Fecs
from fecs import Fecs, CabinsController
from fecs.model import CabinType, FloorType, Vector
from fecs.interfaces import ICircumstance

from java.lang import System,Math

import DefaultCircumstance, CrashCircumstance, FireCircumstance, FloodCircumstance, EarthQuakeCircumstance