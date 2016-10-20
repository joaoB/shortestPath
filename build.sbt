name := """curve"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  cache,
  ws,
  specs2 % Test,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
libraryDependencies ++= Seq("org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test")


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
