package scala

import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper {

  lazy val spark: SparkSession = SparkSession
    .builder()
    .enableHiveSupport()
    .appName("SparkProject")
    .config("spark.hadoop.hive.metastore.uris", "thrift://hive-metastore:9083")
    //.config("spark.sql.warehouse.dir", "/apps/hive/warehouse")
    //.config("spark.sql.hive.metastore.version", "2.3.2")
    .master("local[*]")
    .getOrCreate()
}
