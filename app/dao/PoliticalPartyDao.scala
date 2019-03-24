package dao

import models.PoliticalParty
import DbConfig._

object PoliticalPartyDao {

  import ctx._

  lazy val politicalPartys: Quoted[EntityQuery[PoliticalParty]] = quote(query[PoliticalParty])

  def findAll(): List[PoliticalParty] = {
    run(quote(query[PoliticalParty]))
  }

  def findById(id: Long): Option[PoliticalParty] = {
    run(quote(politicalPartys.filter(_.id == lift(id)))).headOption
  }

  def create(politicalParty: PoliticalParty): PoliticalParty = {
    politicalParty.copy(id = run(quote(politicalPartys.insert(lift(politicalParty)).returning(_.id))))
  }

  def update(politicalParty: PoliticalParty): Long = {
    run(politicalPartys.filter(_.id == lift(politicalParty.id)).update(lift(politicalParty)))
  }

  def delete(politicalPartyId: Long): Long = {
    run(politicalPartys.filter(_.id == lift(politicalPartyId)).delete)
  }

}
