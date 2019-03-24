package models

import play.api.libs.json.{Json, Reads, Writes}

case class User(
                 firstName: String,
                 lastName: String,
                 emailAddress: String,
                 password: String,
                 token: String,
                 role: String
               )

object User {
  implicit val userReads: Reads[User] = Json.reads[User]
  implicit val userWrites: Writes[User] = Json.writes[User]
}
