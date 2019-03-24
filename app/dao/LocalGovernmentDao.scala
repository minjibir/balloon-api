package dao

import models.LocalGovernment
import DbConfig._

object LocalGovernmentDao {

  import ctx._

  lazy val localGovernments: Quoted[EntityQuery[LocalGovernment]] = quote(query[LocalGovernment])

  def findAll(): List[LocalGovernment] = {
    run(quote(query[LocalGovernment]))
  }

  def findById(id: Long): Option[LocalGovernment] = {
    run(quote(localGovernments.filter(_.id == lift(id)))).headOption
  }

  def create(localGovernment: LocalGovernment): LocalGovernment = {
    localGovernment.copy(id = run(quote(localGovernments.insert(lift(localGovernment)).returning(_.id))))
  }

  def update(localGovernment: LocalGovernment): Long = {
    run(localGovernments.filter(_.id == lift(localGovernment.id)).update(lift(localGovernment)))
  }

  def delete(localGovernmentId: Long): Long = {
    run(localGovernments.filter(_.id == lift(localGovernmentId)).delete)
  }

}
