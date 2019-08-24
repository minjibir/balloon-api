name := """balloon"""
organization := "com.cyapex"
maintainer := "jabir.minjibir@gmail.com"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.15",
  "io.getquill" %% "quill-jdbc" % "3.1.0"
)
