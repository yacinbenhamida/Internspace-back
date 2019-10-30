#!/usr/bin/python
from bs4 import BeautifulSoup
import requests as r
import re

clear = lambda : __import__('os').system('clear')

argos	= __import__('sys').argv
argsCh 	= False

if(len(argos) >= 3):
	argsCh = True

class Init_checker:
  def __init__(self,name,country):
    self.name     = name
    self.country  = country
    self.links    = []

  def _rq(self):
    return r.get('https://www.google.com/search?q='+str(self.name)+' enterprise '+str(self.country))

  def _bs(self):
    clear()
    return BeautifulSoup(self._rq().content)
    
  def _fetch(self):
    soup   = self._bs()
    clear()
    linkos = []
    for link in  soup.find_all("a",href=re.compile("(?<=/url\?q=)(htt.*://.*)")):
        linkos.append(re.split(":(?=http)",link["href"].replace("/url?q=","")))
    return linkos



print('les args nayeek !') if argsCh == False else [ print(l) for l in Init_checker(argos[1],argos[2])._fetch()[0:3]]
