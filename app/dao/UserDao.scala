package dao

import models.User
import DbConfig._

object UserDao {

  import ctx._

  lazy val users: Quoted[EntityQuery[User]] = quote(querySchema[User]("users"))

  def findAll(): List[User] = {
    run(quote(query[User]))
  }

  def findById(emailAddress: String): Option[User] = {
    run(quote(users.filter(_.emailAddress == emailAddress))).headOption
  }

  def create(user: User): User = {
    user.copy(emailAddress = run(quote(users.insert(lift(user)).returning(_.emailAddress))))
  }

  def update(user: User): Long = {
    run(users.filter(_.emailAddress == lift(user.emailAddress)).update(lift(user)))
  }

  def delete(email: String): Long = {
    run(users.filter(_.emailAddress == lift(email)).delete)
  }

}
