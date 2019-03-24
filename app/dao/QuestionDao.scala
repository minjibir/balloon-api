package dao

import models.Question
import DbConfig._

object QuestionDao {

  import ctx._

  lazy val questions: Quoted[EntityQuery[Question]] = quote(query[Question])

  def findAll(): List[Question] = {
    run(quote(query[Question]))
  }

  def findById(id: Long): Option[Question] = {
    run(quote(questions.filter(_.id == lift(id)))).headOption
  }

  def create(question: Question): Question = {
    question.copy(id = run(quote(questions.insert(lift(question)).returning(_.id))))
  }

  def update(question: Question): Long = {
    run(questions.filter(_.id == lift(question.id)).update(lift(question)))
  }

  def delete(questionId: Long): Long = {
    run(questions.filter(_.id == lift(questionId)).delete)
  }

}
