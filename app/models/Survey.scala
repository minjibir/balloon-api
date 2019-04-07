package models

import java.time.LocalDateTime

import play.api.libs.json.{Json, Reads, Writes}

case class Survey(
                   id: Long = 0,
                   participantId: Long,
                   takenAt: LocalDateTime = LocalDateTime.now()
                 )

object Survey {
  implicit val surveyReads: Reads[Survey] = Json.reads[Survey]
  implicit val surveyWrites: Writes[Survey] = Json.writes[Survey]
}
