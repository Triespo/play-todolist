package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Task(id: Int, label: String, user_name: String)

object Task {
  
  val task = {
    get[Int]("id") ~ 
    get[String]("label") ~
    get[String]("user_name") map {
     case id~label~user_name => Task(id, label, user_name)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task where user_name = 'anonimo'").as(task *)
  }

  def consult(id: Int): Task = DB.withConnection { implicit c =>
    SQL("select * from task where id = {id}").on('id -> id).as(task.single)
  } 

  def findTarea(id: Int): Option[String] = DB.withConnection { implicit c =>
    SQL("select id, label from task where id = {id}").on('id -> id').as(scalar[String].singleOpt)
  }
  
  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Int):Int = {
    val value:Int = DB.withConnection { 
    implicit c =>
    SQL("delete from task where id = {id}").on(
      'id -> id
    ).executeUpdate()
    }
    value
  }

  def findUser(login: String): Option[String] = DB.withConnection { implicit c =>
    SQL("select login from task_user where login = {login}").on('login -> login).as(scalar[String].singleOpt)
  } 

  def createInUser(user_name: String, label: String){
    DB.withConnection { implicit c =>
      SQL("insert into task (label,user_name) values ({label},{user_name})").on(
        'label -> label, 'user_name -> user_name
      ).executeUpdate()
    }
  }

  def all(user_name: String): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task where user_name = {user_name}").on('user_name -> user_name).as(task *)
  }
}