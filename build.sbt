import org.typelevel.sbt.gha.JavaSpec.Distribution.Zulu

ThisBuild / organization := "co.blocke"
ThisBuild / scalaVersion := "3.4.2"
ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec(Zulu, "8"))
ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest")
ThisBuild / githubWorkflowBuild := Seq(WorkflowStep.Sbt(List("coverage", "test")))
ThisBuild / githubWorkflowBuildPostamble := Seq(
  WorkflowStep.Run(commands = List("echo 'branch ${{github.ref}}'"), name = Some("showme")),
  WorkflowStep.Use(cond = Some("""endsWith(github.ref, 'main')"""), ref = UseRef.Public("coverallsapp","github-action", "v2"), name = Some("Coveralls"))
)

  // final case class Run(commands: List[String], id: Option[String] = None, name: Option[String] = None, cond: Option[String] = None, env: Map[String, String] = Map(), params: Map[String, String] = Map(), timeout: Option[FiniteDuration] = None) extends WorkflowStep
  // final case class Sbt(commands: List[String], id: Option[String] = None, name: Option[String] = None, cond: Option[String] = None, env: Map[String, String] = Map(), params: Map[String, String] = Map(), timeout: Option[FiniteDuration] = None) extends WorkflowStep
  // final case class Use(ref: UseRef, params: Map[String, String] = Map(), id: Option[String] = None, name: Option[String] = None, cond: Option[String] = None, env: Map[String, String] = Map(), timeout: Option[FiniteDuration] = None) extends WorkflowStep

//final case class Public(owner: String, repo: String, ref: String)

lazy val root = project
  .in(file("."))
  .settings(
    name := "foom",
    version := "0.1.0-SNAPSHOT",

    scalacOptions ++= compilerOptions,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )


lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-deprecation",
  // "-explain",'
  "-coverage-exclude-files",
  ".*SJConfig.*",
  "-coverage-exclude-classlikes",
  ".*internal.*",
  "-coverage-exclude-classlikes",
  ".*AnyWriter",
  "-encoding",
  "utf8"
)