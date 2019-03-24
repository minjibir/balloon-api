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
    val optionUser: Option[User] = UserDao.findById(email)

    optionUser match {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound(Json.toJson("User not found"))
      case _ => InternalServerError(Json.toJson("Oops! A server error has occurred!"))
    }
  }

  def postUser: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalUser: Option[User] = request.body.asOpt[User]

      optionalUser match {
        case Some(user) =>
          val newUser: User = UserDao.create(user)
          Created(Json.toJson(newUser))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateUser: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionUser = request.body.asOpt[User]

      optionUser match {
        case Some(p) => Ok(Json.toJson(UserDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteUser(email: String): Action[AnyContent] = Action {
    val userId = UserDao.delete(email)

    userId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete user at the moment!"))
    }
  }

}
