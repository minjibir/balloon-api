package controllers

import dao.GeoPoliticalZoneDao
import javax.inject.Inject
import models.GeoPoliticalZone
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class GeoPoliticalZoneController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def getAllGeoPoliticalZones: Action[AnyContent] = Action(parse.tolerantJson) {
    Ok(Json.toJson(GeoPoliticalZoneDao.findAll()))
  }

  def getGeoPoliticalZone(id: Long): Action[AnyContent] = Action(parse.tolerantJson) {
    val optionGeoPoliticalZone: Option[GeoPoliticalZone] = GeoPoliticalZoneDao.findById(id)

    optionGeoPoliticalZone match {
      case Some(geoPoliticalZone) => Ok(Json.toJson(geoPoliticalZone))
      case None => NotFound(Json.toJson("GeoPoliticalZone not found"))
    }
  }

  def postGeoPoliticalZone: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionalGeoPoliticalZone: Option[GeoPoliticalZone] = request.body.asOpt[GeoPoliticalZone]

      optionalGeoPoliticalZone match {
        case Some(geoPoliticalZone) =>
          Created(Json.toJson(GeoPoliticalZoneDao.create(geoPoliticalZone)))
        case None => BadRequest(request.body)
      }
    }
  }

  def updateGeoPoliticalZone: Action[JsValue] = Action(parse.json) {
    implicit request => {
      val optionGeoPoliticalZone = request.body.asOpt[GeoPoliticalZone]

      optionGeoPoliticalZone match {
        case Some(p) => Ok(Json.toJson(GeoPoliticalZoneDao.update(p)))
        case None => NotFound(Json.toJson("Record notfound!"))
      }
    }
  }

  def deleteGeoPoliticalZone(id: Long): Action[AnyContent] = Action {
    val geoPoliticalZoneId = GeoPoliticalZoneDao.delete(id)

    geoPoliticalZoneId match {
      case id: Long => Ok(Json.toJson(s"Record with ID = $id deleted successfully!"))
    }
  }

}
