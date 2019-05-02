package models

import play.api.libs.json.{Json, Format}

case class Response(
                     id: Long,
                     surveyId: Long,
                     questionId: Long,
                     response: String,
                   )

object Response {
  implicit val responseReads: Format[Response] = Json.format[Response]
}
