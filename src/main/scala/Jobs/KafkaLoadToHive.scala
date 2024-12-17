package Jobs

import model.KafkaMessage
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.col

class KafkaLoadToHive(val spark: SparkSession) extends Serializable{

  val kafka_host = "kafka:29092"
  val topic = "logs"

  import spark.implicits._

  def run(): Unit = {

    val kafkaReader = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", kafka_host)
      .option("subscribe", topic).load()

    val kafkaMessage = kafkaReader
      .withColumn("key", col("key").cast("string"))
      .withColumn("value", col("value").cast("string"))
      .as[KafkaMessage]

    kafkaMessage.write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("row_data.kafka_data")
  }
}

object KafkaLoadToHive extends App with SparkSessionWrapper {

  new KafkaLoadToHive(spark).run()

  spark.stop
}