package dao

import models.Survey
import DbConfig._

object SurveyDao {

  import ctx._

  lazy val surveys: Quoted[EntityQuery[Survey]] = quote(query[Survey])

  def findAll(): List[Survey] = {
    run(quote(query[Survey]))
  }

  def findById(id: Long): Option[Survey] = {
    run(quote(surveys.filter(_.id == lift(id)))).headOption
  }

  def create(survey: Survey): Survey = {
    survey.copy(id = run(quote(surveys.insert(lift(survey)).returning(_.id))))
  }

  def update(survey: Survey): Long = {
    run(surveys.filter(_.id == lift(survey.id)).update(lift(survey)))
  }

  def delete(surveyId: Long): Long = {
    run(surveys.filter(_.id == lift(surveyId)).delete)
  }

}
