package models

import play.api.libs.json.{Json, Reads, Writes}

case class GeoPoliticalZone(
                             id: Long,
                             name: String,
                             abbreviation: String,
                           )

object GeoPoliticalZone {
  implicit val geoPoliticalZoneReads: Reads[GeoPoliticalZone] = Json.reads[GeoPoliticalZone]
  implicit val geoPoliticalZoneWrites: Writes[GeoPoliticalZone] = Json.writes[GeoPoliticalZone]
}
