Water Plant
===========

`Due Date: Sunday September 23, 2012 by 11:55pm`

Multi Treading water plant to flow water.

Description
-----------
In this programming assignment you will simulate the pump management system for 
a water treatment plant like the one depicted here:

![Station](/station.png)

This example water treatment plant has five pumping stations (S0 – S4), each of which has an input 
and output pipe connecting to lines (P0 – P4) that go elsewhere in the plant.  Resources were limited 
when building the plant so each pipe going to the rest of the facility must be shared between two 
pumping  stations.  Since each station simultaneously needs an input and output connection to 
function, access to the shared lines must be strictly regulated.  Flow direction in not important in our 
simulation.
You have been hired to design a simulator for a new water treatment plant being built with the same 
design, but possibly fewer/more stations.  You are to implement this simulator in Java and have 
each station function in its own thread.  A station’s workload is the amount of time that a station 
needs to have access to the input and output pipes during the simulation.  Once a station is granted
access to both pipes it calls its doWork()method during which it will attempt to flow water down 
each pipe (of course it must verify that it has access and isn’t in conflict with another station).  After 
the flow-in and flow-out methods are run, the workload of the station is reduced by 1 and the station 
releases both pipes and signals waiting stations that the pipes are available.  After executing a flow 
and releasing its pipes, a station should sleep for some random period of time.  A station’s thread 
stops running when its workload reaches 0.
