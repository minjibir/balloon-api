package dao

import models.State
import DbConfig._

object StateDao {

  import ctx._

  lazy val states: Quoted[EntityQuery[State]] = quote(query[State])

  def findAll(): List[State] = {
    run(quote(query[State]))
  }

  def findById(id: Long): Option[State] = {
    run(quote(states.filter(_.id == lift(id)))).headOption
  }

  def create(state: State): State = {
    state.copy(id = run(quote(states.insert(lift(state)).returning(_.id))))
  }

  def update(state: State): Long = {
    run(states.filter(_.id == lift(state.id)).update(lift(state)))
  }

  def delete(stateId: Long): Long = {
    run(states.filter(_.id == lift(stateId)).delete)
  }

}
