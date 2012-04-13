#Definizione variabili d'ambiente
CC=g++
CFLAG=


#Definizione variabili personalizzate
BASEDIR=/u/aousterh/IndependentWork/Simulations/momosecpp
SRCDIR=$(BASEDIR)/src
HDRDIR=$(SRCDIR)/include
BINDIR=$(BASEDIR)/bin
EXTLIBDIR=$(BASEDIR)/extlibs

#Expat library
EXPATHDRDIR=$(EXTLIBDIR)/expat/include
EXPATLIBDIR=$(EXTLIBDIR)/expat/lib


LIBS=-lm -lexpat

EXECUTABLE=momose

#User file objs
MODELSOBJS=RandomWalkModelBuilder.o RandomWalkModel.o RandomWalkNode.o \
           RandomWaypointModelBuilder.o RandomWaypointModel.o RandomWaypointNode.o \
	   HotSpotModelBuilder.o HotSpotModel.o HotSpotNode.o \
           PursueModelParser.o PursueModel.o PursueNode.o PursueModelBuilder.o\
           NomadicModelParser.o NomadicModelBuilder.o NomadicModel.o NomadicNode.o \
           EraModelParser.o EraModelBuilder.o EraModel.o EraNode.o \
           SimpleModel.o SimpleNode.o SimpleModelParser.o \
	   MessageModel.o MessageNode.o MessageModelParser.o MessageData.o \
	   RandomWalkMessageModelBuilder.o RandomWalkMessageModel.o RandomWalkMessageNode.o \
	   RandomWaypointMessageModelBuilder.o RandomWaypointMessageModel.o RandomWaypointMessageNode.o	   

DATARECORDERSOBJS= ViewerRecorder.o ViewerRecorderParser.o ViewerRecorderBuilder.o \
	   MessageRecorder.o MessageRecorderBuilder.o


#Definizione dei file oggetto
OBJS=main.o \
     Point2D.o Vector2D.o Ray2D.o    \
     Scenario.o ScenarioElement.o Building.o ScenText.o ScenRectangle.o ScenCircle.o \
     SimTime.o HotSpot.o Node.o \
     Model.o Utils.o \
     AiManager.o PhysicEngine.o Simulation.o \
     XMLParser.o BaseParser.o ScenarioParser.o \
     TimeScenarioParser.o ConfigSimParser.o ModelParser.o \
     SimulationManager.o \
     $(MODELSOBJS) $(DATARECORDERSOBJS)


#Unisco insieme i file oggetto
$(EXECUTABLE): $(OBJS) 
	$(CC) $(CFLAG) -o $@  $^ -L$(EXPATLIBDIR) $(LIBS)

#Compilo i file sorgente
%.o: %.cpp
	$(CC) $(CFLAG) -c $^ -I$(HDRDIR) -I$(EXPATHDRDIR) -I$(EXTLIBDIR)

XMLParser.o: XMLParser.cpp
	$(CC) $(CFLAG) -c $^ -I$(HDRDIR) -I$(EXPATHDRDIR) 

#PHONY target
.PHONY: all clean	

all: $(EXECUTABLE)
	mv $(EXECUTABLE) $(BINDIR)
	#mv *.o $(BINDIR)	

clean:
	rm -f $(SRCDIR)/*.o
	rm -f $(BINDIR)/*.o
	rm -f $(SRCDIR)/*~
 

