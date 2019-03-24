package models

import play.api.libs.json.{Json, Reads, Writes}

case class PoliticalParty(
                             id: Long,
                             name: String,
                             abbreviation: String,
                           )

object PoliticalParty {
  implicit val politicalPartyReads: Reads[PoliticalParty] = Json.reads[PoliticalParty]
  implicit val politicalPartyWrites: Writes[PoliticalParty] = Json.writes[PoliticalParty]
}
