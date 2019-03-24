package dao

import models.GeoPoliticalZone
import DbConfig._

object GeoPoliticalZoneDao {

  import ctx._

  lazy val geoPoliticalZones: Quoted[EntityQuery[GeoPoliticalZone]] = quote(query[GeoPoliticalZone])

  def findAll(): List[GeoPoliticalZone] = {
    run(quote(query[GeoPoliticalZone]))
  }

  def findById(id: Long): Option[GeoPoliticalZone] = {
    run(quote(geoPoliticalZones.filter(_.id == lift(id)))).headOption
  }

  def create(geoPoliticalZone: GeoPoliticalZone): GeoPoliticalZone = {
    geoPoliticalZone.copy(id = run(quote(geoPoliticalZones.insert(lift(geoPoliticalZone)).returning(_.id))))
  }

  def update(geoPoliticalZone: GeoPoliticalZone): Long = {
    run(geoPoliticalZones.filter(_.id == lift(geoPoliticalZone.id)).update(lift(geoPoliticalZone)))
  }

  def delete(geoPoliticalZoneId: Long): Long = {
    run(geoPoliticalZones.filter(_.id == lift(geoPoliticalZoneId)).delete)
  }

}
