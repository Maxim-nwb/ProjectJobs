package model

case class LogThree(
                     application: Int,
                     timestamp: String,
                     log_type: String,
                     error_code: Option[String],
                     user_id: Int,
                     user_position_id: Int
                   )
