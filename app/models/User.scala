package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class User(login: String)

object User{

   val user = {
      get[String] login map{
         case login => User(login)
      }
   }

   def findUser(user: String): Option[Int] = DB.withConnection { implicit c =>
      SQL("select * from task_user where login = {user}").on('login -> user).as(Int)

   }

   //ef all(user: String): List[Task]
}