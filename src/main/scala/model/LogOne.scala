package model

case class LogOne(
                   application: Int,
                   timestamp: String,
                   log_type: String,
                   error_code: Option[String],
                   count_operations: Int,
                   var type_work_counts: Int
                 ) {
  def +(that: LogOne): LogOne = {
    this.type_work_counts += that.type_work_counts
    this
  }
}
