package models

import java.time.LocalDateTime

import play.api.libs.json.{Json, Reads, Writes}

case class Question(
  id: Long,
  text: String,
  choices: String,
  addedAt: LocalDateTime = LocalDateTime.now(),
)

object Question {
  implicit val questionWrites = Json.format[Question]
}
