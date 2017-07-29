resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "jgit-repo" at "http://download.eclipse.org/jgit/maven"
)

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.0")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.4")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0-M1")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.0.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-proguard" % "0.2.3")
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.5.2")
addSbtPlugin("com.thoughtworks.sbt-api-mappings" % "sbt-api-mappings" % "1.0.0")

addSbtPlugin("info.hupel" % "sbt-libisabelle" % "0.5.1")
