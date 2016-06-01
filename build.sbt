name := "scala-existentials"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "johnreed2 bintray" at "http://dl.bintray.com/content/johnreed2/maven"

libraryDependencies += "scala.trace" %% "scala-trace-debug" % "2.2.14"

// "-Xprint:typer"

scalacOptions ++= Seq("-Xprint:parser", "-unchecked", "-deprecation", "-feature", "-Xlint", "-Xfatal-warnings", "-Yinline-warnings", "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-nullary-unit")
    