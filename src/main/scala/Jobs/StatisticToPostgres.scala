package Jobs

import model.KafkaMessage
import org.apache.spark.sql.{SaveMode, SparkSession}

class StatisticToPostgres(val spark: SparkSession) extends Serializable{

  def run(): Unit = {

    spark.read
      .table("showcase.statistic1")
      .write
      .mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://db:5432/statistic")
      .option("user", "user")
      .option("password", "12345")
      .option("dbtable", "statistic1")
      .save()

    spark.read
      .table("showcase.statistic2")
      .write
      .mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://db:5432/statistic")
      .option("user", "user")
      .option("password", "12345")
      .option("dbtable", "statistic2")
      .save()

    spark.read
      .table("showcase.statistic3")
      .write
      .mode(SaveMode.Overwrite)
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://db:5432/statistic")
      .option("user", "user")
      .option("password", "12345")
      .option("dbtable", "statistic3")
      .save()

  }
}

object StatisticToPostgres extends App with SparkSessionWrapper {

  new StatisticToPostgres(spark).run()

  spark.stop
}