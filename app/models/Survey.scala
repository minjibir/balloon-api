package models

import play.api.libs.json.{Json, Reads, Writes}

case class Survey(
                             id: Long,
                             name: String,
                             abbreviation: String,
                           )

object Survey {
  implicit val surveyReads: Reads[Survey] = Json.reads[Survey]
  implicit val surveyWrites: Writes[Survey] = Json.writes[Survey]
}
