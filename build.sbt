name := """beehive-rest-api"""
organization := "com.cyapex"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1208",
  "io.getquill" %% "quill-jdbc" % "3.1.0"
)

//
//libraryDependencies ++= Seq(
//  "mysql" % "mysql-connector-java" % "8.0.15",
//  "io.getquill" %% "quill-jdbc" % "3.1.0"
//)