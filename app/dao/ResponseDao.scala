package dao

import models.Response
import DbConfig._

object ResponseDao {

  import ctx._

  lazy val responses: Quoted[EntityQuery[Response]] = quote(query[Response])

  def findAll(): List[Response] = {
    run(quote(query[Response]))
  }

  def findById(id: Long): Option[Response] = {
    run(quote(responses.filter(_.id == lift(id)))).headOption
  }

  def saveReponses(responses: List[Response]) = {
    try {
      run(quote(liftQuery(responses).foreach(e => query[Response].insert(e))))
    } catch {
      case e: Exception => println(e)
    }
  }

  def create(response: Response): Response = {
    response.copy(
      id = run(quote(responses.insert(lift(response)).returning(_.id)))
    )
  }

  def update(response: Response): Long = {
    run(responses.filter(_.id == lift(response.id)).update(lift(response)))
  }

  def delete(responseId: Long): Long = {
    run(responses.filter(_.id == lift(responseId)).delete)
  }

}
