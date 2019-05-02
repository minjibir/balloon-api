package controllers

import dao.ResponseDao
import javax.inject.Inject
import models.Response
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ResponseController @Inject()(cc: ControllerComponents, implicit val ec: ExecutionContext)
  extends AbstractController(cc) {

  def getAllResponses: Action[AnyContent] = Action.async {
    Future {
      Ok(Json.toJson(ResponseDao.findAll()))
    }
  }

  def getResponseBySurveyId(surveyId: Long): Action[AnyContent] = Action.async {
    Future {
      Ok(Json.toJson(ResponseDao.findBySurveyId(surveyId)))
    }
  }

  def getResponse(id: Long): Action[AnyContent] = Action.async {
    Future {
      ResponseDao.findById(id) match {
        case Some(response) => Ok(Json.toJson(response))
        case None => NotFound(Json.toJson("Response not found"))
      }
    }
  }

  def postResponse: Action[JsValue] = Action.async(parse.json) {
    request => {
      request.body.validate[List[Response]].fold(
        error => Future.successful(BadRequest(Json.toJson(error.toString()))),
        responses => {
          Future(Created(Json.toJson(ResponseDao.saveResponses(responses))))
        }
      )
    }
  }

  def updateResponse: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionResponse = request.body.asOpt[Response]

      optionResponse match {
        case Some(p) => Ok(Json.toJson(ResponseDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteResponse(id: Long): Action[AnyContent] = Action.async {
    Future {
      ResponseDao.delete(id) match {
        case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      }
    }
  }

}
