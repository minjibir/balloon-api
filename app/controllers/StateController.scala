package controllers

import dao.StateDao
import javax.inject.Inject
import models.State
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class StateController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllStates: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(StateDao.findAll()))
  }

  def getState(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    StateDao.findById(id) match {
      case Some(state) => Ok(Json.toJson(state))
      case None => NotFound(Json.toJson("State not found"))
    }
  }

  def postState: Action[JsValue] = Action(parse.json) {
    implicit request => {
      request.body.asOpt[State] match {
        case Some(state) => Created(Json.toJson(StateDao.create(state)))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateState: Action[JsValue] = Action(parse.json) {
    implicit request =>
      request.body.asOpt[State] match {
        case Some(p) => Ok(Json.toJson(StateDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
  }

  def deleteState(id: Long): Action[AnyContent] = Action {
    StateDao.delete(id) match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
