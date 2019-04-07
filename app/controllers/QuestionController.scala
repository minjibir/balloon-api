package controllers

import java.time.LocalDateTime

import dao.QuestionDao
import javax.inject.Inject
import models.Question
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class QuestionController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllQuestions: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(QuestionDao.findAll()))
  }

  def getQuestion(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionQuestion: Option[Question] = QuestionDao.findById(id)

    optionQuestion match {
      case Some(question) => Ok(Json.toJson(question))
      case None => NotFound(Json.toJson("Question not found"))
    }
  }

  def postQuestion: Action[JsValue] = Action(parse.json) {
    implicit request => {
      request.body.asOpt[Question] match {
        case Some(question) =>
          Created(Json.toJson(QuestionDao.create(Question(0, question.text, LocalDateTime.now()))))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateQuestion: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionQuestion = request.body.asOpt[Question]

      optionQuestion match {
        case Some(p) => Ok(Json.toJson(QuestionDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteQuestion(id: Long): Action[AnyContent] = Action {
    QuestionDao.delete(id) match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
