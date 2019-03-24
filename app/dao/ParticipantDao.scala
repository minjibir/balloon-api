package dao

import models.Participant
import DbConfig._

object ParticipantDao {

  import ctx._

  lazy val participants: Quoted[EntityQuery[Participant]] = quote(query[Participant])

  def findAll(): List[Participant] = {
    run(quote(query[Participant]))
  }

  def findById(id: Long): Option[Participant] = {
    run(quote(participants.filter(_.id == lift(id)))).headOption
  }

  def create(participant: Participant): Participant = {
    participant.copy(id = run(quote(participants.insert(lift(participant)).returning(_.id))))
  }

  def update(participant: Participant): Long = {
    run(participants.filter(_.id == lift(participant.id)).update(lift(participant)))
  }

  def delete(participantId: Long): Long = {
    run(participants.filter(_.id == lift(participantId)).delete)
  }

}
