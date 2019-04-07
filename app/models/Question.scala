package models

import java.time.LocalDateTime

import play.api.libs.json.{Json, Reads, Writes}

case class Question(
                     id: Long,
                     text: String,
                     addedAt: LocalDateTime = LocalDateTime.now()
                   )

object Question {
  implicit val questionReads: Reads[Question] = Json.reads[Question]
  implicit val questionWrites: Writes[Question] = Json.writes[Question]
}
