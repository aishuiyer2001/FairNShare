<<<<<<< HEAD
name := """NewApp"""
=======
name := """FairNShare-Master"""
>>>>>>> 565084110e3ade1909c471a66463d552f9b82294

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)
