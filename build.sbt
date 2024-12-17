ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.12.15"

val sparkVersion = "3.2.1"
val sparkHiveVersion = "3.2.0"
val kafkaClientsVersion = "2.3.0"

lazy val sparkDependencies = Seq(
  "org.apache.spark"         %% "spark-core"          % sparkVersion % "provided",
  "org.apache.spark"         %% "spark-sql"           % sparkVersion,
  "org.apache.spark"         %% "spark-streaming"     % sparkVersion,
  "org.apache.spark"         %% "spark-hive"          % sparkHiveVersion,
  "org.apache.spark"         % "spark-streaming-kafka-0-10_2.12" % sparkVersion,
  "org.apache.hive"          % "hive-jdbc"            % "3.1.3",
  "org.apache.hive"          % "hive-metastore"       % "2.3.9",
  "org.apache.hadoop"        % "hadoop-common"        % "2.7.4",
  "org.apache.hadoop"        % "hadoop-auth"          % "3.2.3",

  "org.apache.kafka"         % "kafka-clients"        % kafkaClientsVersion,
  "org.apache.spark"         % "spark-sql-kafka-0-10_2.12" % sparkVersion,

  "org.apache.logging.log4j" % "log4j-core"             % "2.20.0",
  "org.apache.thrift"        % "libthrift"              % "0.19.0",
  "io.netty"                 % "netty-all"              % "4.1.97.Final"
)

lazy val root = (project in file("."))
  .settings(
    name := "ProjectJobs",
    libraryDependencies ++= sparkDependencies,
    javacOptions ++= Seq("-source", "16"),
    javaOptions ++= Seq( // Spark-specific JVM options
      "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    ), //--add-exports java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED
    compileOrder := CompileOrder.JavaThenScala
  )

 assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}
