package models

import play.api.libs.json.{Json, Reads, Writes}

case class Response(
                     id: Long,
                     name: String,
                     abbreviation: String,
                   )

object Response {
  implicit val responseReads: Reads[Response] = Json.reads[Response]
  implicit val responseWrites: Writes[Response] = Json.writes[Response]
}
