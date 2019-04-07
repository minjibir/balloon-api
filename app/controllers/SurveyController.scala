package controllers

import java.time.LocalDateTime;
import dao.SurveyDao
import javax.inject.Inject
import models.Survey
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class SurveyController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllSurveys: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(SurveyDao.findAll()))
  }

  def getSurvey(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionSurvey: Option[Survey] = SurveyDao.findById(id)

    optionSurvey match {
      case Some(survey) => Ok(Json.toJson(survey))
      case None => NotFound(Json.toJson("Survey not found"))
    }
  }

  def postSurvey: Action[JsValue] = Action(parse.json) {
    implicit request => {
      request.body.asOpt[Survey] match {
        case Some(survey) => {
          Created(Json.toJson(SurveyDao.create(Survey(0, survey.participantId, LocalDateTime.now()))))
        }
        case None => BadRequest(request.body)
      }
    }
  }

  def updateSurvey: Action[JsValue] = Action(parse.json) {
    implicit request =>
      request.body.asOpt[Survey] match {
        case Some(p) => Ok(Json.toJson(SurveyDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
  }

  def deleteSurvey(id: Long): Action[AnyContent] = Action {
    SurveyDao.delete(id) match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
