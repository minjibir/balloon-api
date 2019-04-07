package controllers

import dao.LocalGovernmentDao
import javax.inject.Inject
import models.LocalGovernment
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class LocalGovernmentController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllLocalGovernments: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(LocalGovernmentDao.findAll()))
  }

  def getLocalGovernment(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionLocalGovernment: Option[LocalGovernment] = LocalGovernmentDao.findById(id)

    optionLocalGovernment match {
      case Some(localGovernment) => Ok(Json.toJson(localGovernment))
      case None => NotFound(Json.toJson("LocalGovernment not found"))
    }
  }

  def postLocalGovernment: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalLocalGovernment: Option[LocalGovernment] = request.body.asOpt[LocalGovernment]

      optionalLocalGovernment match {
        case Some(localGovernment) => Created(Json.toJson(LocalGovernmentDao.create(localGovernment)))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateLocalGovernment: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionLocalGovernment = request.body.asOpt[LocalGovernment]

      optionLocalGovernment match {
        case Some(p) => Ok(Json.toJson(LocalGovernmentDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteLocalGovernment(id: Long): Action[AnyContent] = Action {
    val localGovernmentId = LocalGovernmentDao.delete(id)

    localGovernmentId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
