package model


import java.sql.Timestamp

case class KafkaMessage(
                         key: Option[String],
                         value: String,
                         topic: String,
                         partition: Int,
                         offset: Long,
                         timestamp: Timestamp,
                         timestampType: Int
                       )

case class KafkaGet(

                         offset: Long,
                         partition: Long,
                         timestamp: String,
                         timestampType: Long,
                         value: String
                       )
