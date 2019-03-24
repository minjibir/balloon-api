package models

import play.api.libs.json.{Json, Reads, Writes}

case class LocalGovernment(
                            id: Long,
                            name: String,
                            stateId: Long,
                          )

object LocalGovernment {
  implicit val localGovernmentReads: Reads[LocalGovernment] = Json.reads[LocalGovernment]
  implicit val localGovernmentWrites: Writes[LocalGovernment] = Json.writes[LocalGovernment]
}
