package Jobs

import model.KafkaMessage
import org.apache.spark.sql
import org.apache.spark.sql.functions.{col, expr}
import org.apache.spark.sql.{SaveMode, SparkSession, functions}


class SortByApps(val spark: SparkSession) extends Serializable{

  import spark.implicits._

  def run(): Unit = {

    val kafkaDataDS = spark.read
      .table("row_data.kafka_data")

    val widthApplication = kafkaDataDS.withColumn("application",
      expr("from_json(value, 'STRUCT<`application`: INT>')")
    )

    widthApplication.filter($"application.application" === 1 )
      .select(col("value"), col("timestamp"))
      .withColumn("application", functions.json_tuple(functions.col("value"), "application")).withColumn("application", $"application".cast(sql.types.IntegerType))
      .withColumn("log_type", functions.json_tuple(functions.col("value"), "log_type"))
      .withColumn("error_code", functions.json_tuple(functions.col("value"), "error_code"))
      .withColumn("count_operations", functions.json_tuple(functions.col("value"), "count_operations")).withColumn("count_operations", $"count_operations".cast(sql.types.IntegerType))
      .withColumn("type_work_counts", functions.json_tuple(functions.col("value"), "type_work_counts")).withColumn("type_work_counts", $"type_work_counts".cast(sql.types.IntegerType))
      .drop("value")
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.logs1")

    widthApplication.filter($"application.application" === 2 )
      .select(col("value"), col("timestamp"))
      .withColumn("application", functions.json_tuple(functions.col("value"), "application")).withColumn("application", $"application".cast(sql.types.IntegerType))
      .withColumn("log_type", functions.json_tuple(functions.col("value"), "log_type"))
      .withColumn("error_code", functions.json_tuple(functions.col("value"), "error_code"))
      .withColumn("event_id", functions.json_tuple(functions.col("value"), "event_id")).withColumn("event_id", $"event_id".cast(sql.types.IntegerType))
      .withColumn("event_name", functions.json_tuple(functions.col("value"), "event_name"))
      .withColumn("event_status", functions.json_tuple(functions.col("value"), "event_status"))
      .drop("value")
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.logs2")

    widthApplication.filter($"application.application" === 3 )
      .select(col("value"), col("timestamp"))
      .withColumn("application", functions.json_tuple(functions.col("value"), "application")).withColumn("application", $"application".cast(sql.types.IntegerType))
      .withColumn("log_type", functions.json_tuple(functions.col("value"), "log_type"))
      .withColumn("error_code", functions.json_tuple(functions.col("value"), "error_code"))
      .withColumn("user_id", functions.json_tuple(functions.col("value"), "user_id")).withColumn("user_id", $"user_id".cast(sql.types.IntegerType))
      .withColumn("user_position_id", functions.json_tuple(functions.col("value"), "user_position_id")).withColumn("user_position_id", $"user_position_id".cast(sql.types.IntegerType))
      .drop("value")
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.logs3")

  }
}

object SortByApps extends App with SparkSessionWrapper {

  new SortByApps(spark).run()

  spark.stop
}