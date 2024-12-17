package model

case class LogTwo(
                   application: Int,
                   timestamp: String,
                   log_type: String,
                   error_code: Option[String],
                   event_id: Int,
                   event_name: String,
                   event_status: String,
                 )
