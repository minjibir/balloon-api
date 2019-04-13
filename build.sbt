name := """beehive-rest-api"""
organization := "com.cyapex"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.15",
  "io.getquill" %% "quill-jdbc" % "3.1.0"
)
