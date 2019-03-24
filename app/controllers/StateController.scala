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
    val optionState: Option[State] = StateDao.findById(id)

    optionState match {
      case Some(state) => Ok(Json.toJson(state))
      case None => NotFound(Json.toJson("State not found"))
      case _ => InternalServerError(Json.toJson("Oops! A server error has occurred!"))
    }
  }

  def postState: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalState: Option[State] = request.body.asOpt[State]

      optionalState match {
        case Some(state) =>
          Created(Json.toJson(StateDao.create(state)))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateState: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionState = request.body.asOpt[State]

      optionState match {
        case Some(p) => Ok(Json.toJson(StateDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteState(id: Long): Action[AnyContent] = Action {
    val stateId = StateDao.delete(id)

    stateId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete state at the moment!"))
    }
  }

}
