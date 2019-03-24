package controllers

import dao.PoliticalPartyDao
import javax.inject.Inject
import models.PoliticalParty
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class PoliticalPartyController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllPoliticalPartys: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(PoliticalPartyDao.findAll()))
  }

  def getPoliticalParty(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionPoliticalParty: Option[PoliticalParty] = PoliticalPartyDao.findById(id)

    optionPoliticalParty match {
      case Some(politicalParty) => Ok(Json.toJson(politicalParty))
      case None => NotFound(Json.toJson("PoliticalParty not found"))
      case _ => InternalServerError(Json.toJson("Oops! A server error has occurred!"))
    }
  }

  def postPoliticalParty: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalPoliticalParty: Option[PoliticalParty] = request.body.asOpt[PoliticalParty]

      optionalPoliticalParty match {
        case Some(politicalParty) =>
          val newPoliticalParty: PoliticalParty = PoliticalPartyDao.create(politicalParty)
          Created(Json.toJson(newPoliticalParty))
        case None => BadRequest(request.body)
      }
    }
  }

  def updatePoliticalParty: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionPoliticalParty = request.body.asOpt[PoliticalParty]

      optionPoliticalParty match {
        case Some(p) => Ok(Json.toJson(PoliticalPartyDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deletePoliticalParty(id: Long): Action[AnyContent] = Action {
    val politicalPartyId = PoliticalPartyDao.delete(id)

    politicalPartyId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
      case _ => InternalServerError(Json.toJson("Unable to delete politicalParty at the moment!"))
    }
  }

}
