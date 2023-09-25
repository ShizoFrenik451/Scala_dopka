ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "DopkaScalaTextSharing"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15"

libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.17.4"

val http4sVersion = "0.23.19"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion)

val doobieVersion = "1.0.0-RC2"

libraryDependencies += "com.typesafe" % "config" % "1.4.2"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.5.4",
  "org.tpolecat" %% "doobie-core"      % doobieVersion,
  "org.tpolecat" %% "doobie-h2"        % doobieVersion,
  "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"    % "1.0.0-RC2"
)

val circeVersion = "0.14.1"

Compile / doc / scalacOptions := Seq("-groups", "-implicits")

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect-kernel" % "3.5.0",
  "org.typelevel" %% "cats-effect-std" % "3.5.0"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-literal" % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion
)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % "0.23.14",
)

