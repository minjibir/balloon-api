package controllers

import dao.UserDao
import javax.inject.Inject
import models.User
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

class AccessController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val emailAddress: String = (request.body \ "emailAddress").asOpt[String].getOrElse("")
      val password: String = (request.body \ "password").asOpt[String].getOrElse("")

      UserDao
        .findById(emailAddress)
        .filter(_.password.contentEquals(password))
        .get match {
        case user: User =>
          // Compute jwt and send it in the header
          Ok(Json.toJson(s"Welcome back ${user.firstName} ${user.lastName}"))
        case _ => NotFound(Json.toJson("username and password combination are incorrect"))
      }
    }
  }

}
