com.twitter.scrooge.ScroogeSBT.newSettings

com.github.retronym.SbtOneJar.oneJarSettings

name := "Nebula"

version := "1.0"

scalaVersion := "2.10.4"

unmanagedJars in Compile += file("lib/cassovary-core_2.10-3.3.1.jar")

resolvers ++= Seq(
  "twttr" at "http://maven.twttr.com/",
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "org.mapdb" % "mapdb" % "1.0.5",
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "com.twitter" %% "scrooge-core" % "3.16.3",
  "com.twitter" %% "finagle-stats" % "6.20.0",
  "com.twitter" %% "finagle-thrift" % "6.20.0",
  "com.twitter" %% "finagle-http" % "6.20.0",
  "com.twitter" %% "ostrich" % "9.1.0",
  "com.twitter.common" % "metrics" % "0.0.29",
  "it.unimi.dsi" % "fastutil" % "6.4.4",
  "com.twitter" %% "twitter-server" % "1.1.0",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.1.+" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
