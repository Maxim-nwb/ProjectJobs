package Jobs

import model.{LogOne, LogThree, LogTwo, ResultsStatisticOne, ResultsStatisticThree, ResultsStatisticTwo}
import org.apache.spark.sql.{SaveMode, SparkSession}

class StatisticByApps(val spark: SparkSession) extends Serializable{

  import spark.implicits._

  def run(): Unit = {

    val logOneDS = spark.read
      .table("showcase.logs1")
      .as[LogOne]

    val logTwoDS = spark.read
      .table("showcase.logs2")
      .as[LogTwo]

    val logThreeDS = spark.read
      .table("showcase.logs3")
      .as[LogThree]

    logOneDS
      .groupByKey(i => (i.error_code, i.log_type))
      .mapGroups {(k,v) =>
        val t = v.reduce(_+_)
        ResultsStatisticOne(k._1, k._2, t.type_work_counts)
      }
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.statistic1")

    logTwoDS
      .groupByKey(i => (i.event_status, i.event_name))
      .count()
      .map{ case (k,v) =>
        ResultsStatisticTwo(k._1,k._2,v) }
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.statistic2")

    logThreeDS
      .filter(_.error_code.isDefined)
      .groupByKey(i => (i.error_code, i.user_id))
      .count()
      .map{ case (k,v) =>
        ResultsStatisticThree(k._1,k._2,v) }
      .write
      .format("orc")
      .mode(SaveMode.Overwrite)
      .saveAsTable("showcase.statistic3")


  }
}

object StatisticByApps extends App with SparkSessionWrapper {

  new StatisticByApps(spark).run()

  spark.stop
}