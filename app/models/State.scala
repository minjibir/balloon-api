package models

import play.api.libs.json.{Json, Reads, Writes}

case class State(
                  id: Long = 0,
                  name: String,
                  zoneId: Long
                )

object State {
  implicit val stateReads: Reads[State] = Json.reads[State]
  implicit val stateWrites: Writes[State] = Json.writes[State]
}
