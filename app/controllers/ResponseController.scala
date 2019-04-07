package controllers

import dao.ResponseDao
import javax.inject.Inject
import models.Response
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class ResponseController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllResponses: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(ResponseDao.findAll()))
  }

  def getResponse(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionResponse: Option[Response] = ResponseDao.findById(id)

    optionResponse match {
      case Some(response) => Ok(Json.toJson(response))
      case None => NotFound(Json.toJson("Response not found"))
    }
  }

  def postResponse: Action[JsValue] = Action(parse.json) {
    implicit request => {
      request.body match {
        case JsArray(responses) =>
          responses.map {
            e =>
              e.asOpt[Response] match {
                case Some(r) => ResponseDao.create(Response(r.id, r.surveyId, r.questionId, r.response))
              }
          }
          Created("Successful.")
        case _ => BadRequest("Bad request.")
      }
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

  def deleteResponse(id: Long): Action[AnyContent] = Action {
    val responseId = ResponseDao.delete(id)

    responseId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
