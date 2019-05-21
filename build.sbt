name := """play-java-event-loggerr"""

version := "2.7.x"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

inThisBuild(
  List(
    dependencyOverrides := Seq(
      "org.codehaus.plexus" % "plexus-utils" % "3.0.18",
      "com.google.code.findbugs" % "jsr305" % "3.0.1",
      "com.google.guava" % "guava" % "22.0"
    ),
    scalaVersion := "2.12.8"
  )
)


libraryDependencies += guice
libraryDependencies += javaJpa
libraryDependencies += "com.h2database" % "h2" % "1.4.199"

libraryDependencies += "org.hibernate" % "hibernate-core" % "5.4.2.Final"
libraryDependencies += "io.dropwizard.metrics" % "metrics-core" % "3.2.6"
libraryDependencies += "com.palominolabs.http" % "url-builder" % "1.1.0"
libraryDependencies += "net.jodah" % "failsafe" % "1.0.5"

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)

PlayKeys.externalizeResources := false

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation"
)
