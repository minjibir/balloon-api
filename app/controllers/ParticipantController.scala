package controllers

import dao.ParticipantDao
import javax.inject.Inject
import models.Participant
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class ParticipantController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllParticipants: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(ParticipantDao.findAll()))
  }

  def getParticipant(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionParticipant: Option[Participant] = ParticipantDao.findById(id)

    optionParticipant match {
      case Some(participant) => Ok(Json.toJson(participant))
      case None => NotFound(Json.toJson("Participant not found"))
    }
  }

  def postParticipant: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalParticipant: Option[Participant] = request.body.asOpt[Participant]

      optionalParticipant match {
        case Some(participant) => Created(Json.toJson(ParticipantDao.create(participant)))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateParticipant: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionParticipant = request.body.asOpt[Participant]

      optionParticipant match {
        case Some(p) => Ok(Json.toJson(ParticipantDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteParticipant(id: Long): Action[AnyContent] = Action {
    val participantId = ParticipantDao.delete(id)

    participantId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
