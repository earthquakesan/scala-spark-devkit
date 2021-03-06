val sparkCore = "org.apache.spark" %% "spark-core" % "1.6.1"
val hadoopClient = "org.apache.hadoop" % "hadoop-client" % "2.7.1"

lazy val commonSettings = Seq(
  name := "scala-spark-devkit",
  organization := "org.example",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += sparkCore,
    libraryDependencies += hadoopClient
  )
