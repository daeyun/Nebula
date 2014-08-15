logLevel := Level.Warn

resolvers ++= Seq(
  "twttr" at "http://maven.twttr.com/",
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("releases")
)

addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "3.12.3")

addSbtPlugin("org.scala-sbt.plugins" % "sbt-onejar" % "0.8")
