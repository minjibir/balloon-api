package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class AccessController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def login() = Action(parse.json) {
    //    implicit request => {
    //      val emailAddress: JsLookupResult = request.body \ "emailAddress"
    //      val password: JsLookupResult = request.body \ "password"
    //
    //      val user = UserDao.findById(emailAddress)
    //    }
    Ok
  }
}