name := """RestApplication"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "4.1.9.Final",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "commons-codec" % "commons-codec" % "1.7",
  javaCore,
  javaJdbc,
  javaEbean,
  javaJpa,
  cache,
  filters,  
  javaWs
)
