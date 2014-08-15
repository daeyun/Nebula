com.twitter.scrooge.ScroogeSBT.newSettings

com.github.retronym.SbtOneJar.oneJarSettings

name := "Nebula"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "org.mapdb" % "mapdb" % "1.0.5",
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "com.twitter" %% "scrooge-core" % "3.16.3",
  "com.twitter" %% "finagle-stats" % "6.20.0",
  "com.twitter" %% "finagle-thrift" % "6.20.0",
  "com.twitter" %% "finagle-http" % "6.20.0",
  "com.twitter" %% "cassovary-core" % "3.3.1",
  "it.unimi.dsi" % "fastutil" % "6.4.4",
  "com.twitter" %% "twitter-server" % "1.1.0",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.1.+" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test"
)
