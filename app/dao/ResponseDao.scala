package dao

import DbConfig.ctx
import models.Response

object ResponseDao {

  import ctx._

  lazy val responses: Quoted[EntityQuery[Response]] = quote(query[Response])

  def findAll(): List[Response] = {
    run(quote(query[Response]))
  }

  def findBySurveyId(surveyId: Long): List[Response] = {
    run(quote(responses.filter(_.surveyId == lift(surveyId))))
  }

  def findById(id: Long): Option[Response] = {
    run(quote(responses.filter(_.id == lift(id)))).headOption
  }

  def saveResponses(responses: List[Response]): List[Long] = {
    run(quote(liftQuery(responses).foreach(e => query[Response].insert(e))))
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
