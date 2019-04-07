package controllers

import dao.UserDao
import javax.inject.Inject
import models.User
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class UserController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllUsers: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(UserDao.findAll()))
  }

  def getUser(email: String): Action[AnyContent] = Action(parse.tolerantJson) {
    UserDao.findById(email) match {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound(Json.toJson("User not found"))
    }
  }

  def postUser: Action[JsValue] = Action(parse.json) {
    implicit request =>
      request.body.asOpt[User] match {
        case Some(user) =>
          val newUser: User = UserDao.create(user)
          Created(Json.toJson(newUser))
        case None => BadRequest(request.body)
      }
  }

  def updateUser: Action[JsValue] = Action(parse.json) {
    implicit request =>
      request.body.asOpt[User] match {
        case Some(p) => Ok(Json.toJson(UserDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
  }

  def deleteUser(email: String): Action[AnyContent] = Action {
    UserDao.delete(email) match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete user at the moment!"))
    }
  }

}
