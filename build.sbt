name := "unitsbuilder"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.mockito" % "mockito-core" % "1.9.5",
  "junit" % "junit" % "4.12" % "test",
  "org.apache.commons" % "commons-lang3" % "3.3.2"
)