package models

import play.api.libs.json.{Json, Reads, Writes, Format}

case class Participant(
                        id: Long,
                        firstName: String,
                        middleName: Option[String],
                        lastName: String,
                        gender: String,
                        maritalStatus: String,
                        phoneNumber: Option[String],
                        emailAddress: Option[String],
                        employmentStatus: String,
                        partyId: Option[Long],
                        localGovernmentId: Long
                      )

object Participant {
  implicit val participantReads: Reads[Participant] = Json.reads[Participant]
  implicit val participantWrites: Writes[Participant] = Json.writes[Participant]
  implicit val participantFormat: Format[Participant] = Json.format[Participant]
}
