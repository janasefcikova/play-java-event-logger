# play-java-event-logger
Simple play framework application demonstating rest api services with in memory storage.

## Used stack
Play 2, java, Guice, H2, JPA, Hibernate, asynchronous calls

## Usage
Install [sbt](https://www.scala-sbt.org/). 

Run application 
```
> cd play-java-event-logger
> sbt
> run
```

Test application
```
> cd play-java-event-logger
> sbt
play-java-event-logger> test
```

## Development hints 

Continunous development
```
> cd play-java-event-logger
> sbt
play-java-event-logger> ~compile  // Continuous compilation, press Enter to type next commands after compile
play-java-event-logger> ~run      // Continuous run
```
### Working in Eclipse

Install Scala-IDE and TM Terminal plugins into Eclipse.

Create eclipse project from the project as follows 
```
> cd play-java-event-logger
> sbt eclipse
```

In Eclipse, select File->Import->Existing project into Workspace. 

Open installed terminal in Eclipse, select Window->Show view->Other->Terminal->Terminal. 
If console doesn't start, press Open a terminal icon on the right. 

Run project as above
```
> cd play-java-event-logger
> sbt
play-java-event-logger> ~compile
play-java-event-logger> ~run
```

Hope that Eclipse registers changes :)
