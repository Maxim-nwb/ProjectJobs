package model

case class ResultsStatisticOne(
                                error_code: Option[String],
                                log_type: String,
                                type_work_counts: Int
                  )

case class ResultsStatisticTwo(
                                event_status: String,
                                event_name: String,
                                count: Long
                              )

case class ResultsStatisticThree(
                                error_code: Option[String],
                                user_id: Int,
                                count: Long
                              )