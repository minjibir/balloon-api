package controllers

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
      case _ => InternalServerError(Json.toJson("Oops! A server error has occurred!"))
    }
  }

  def postSurvey: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalSurvey: Option[Survey] = request.body.asOpt[Survey]

      optionalSurvey match {
        case Some(survey) =>
          val newSurvey: Survey = SurveyDao.create(survey)
          Created(Json.toJson(newSurvey))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateSurvey: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionSurvey = request.body.asOpt[Survey]

      optionSurvey match {
        case Some(p) => Ok(Json.toJson(SurveyDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteSurvey(id: Long): Action[AnyContent] = Action {
    val surveyId = SurveyDao.delete(id)

    surveyId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete survey at the moment!"))
    }
  }

}
