package controllers

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
      case _ => InternalServerError(Json.toJson("Oops! A server error has occurred!"))
    }
  }

  def postQuestion: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalQuestion: Option[Question] = request.body.asOpt[Question]

      optionalQuestion match {
        case Some(question) =>
          val newQuestion: Question = QuestionDao.create(question)
          Created(Json.toJson(newQuestion))
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
    val questionId = QuestionDao.delete(id)

    questionId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete question at the moment!"))
    }
  }

}
