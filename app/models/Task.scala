package models

import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Task(id: Int, label: String, user_name: String, task_date: Option[Date], category: Option[String])

object Task {
  
  val task = {
    get[Int]("id") ~ 
    get[String]("label") ~
    get[String]("user_name") ~
    get[Option[Date]]("task_date") ~
    get[Option[String]]("category") map {
     case id~label~user_name~task_date~category => Task(id, label, user_name,task_date,category)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task where user_name = 'anonimo'").as(task *)
  }

  def consult(id: Int): Task = DB.withConnection { implicit c =>
    SQL("select * from task where id = {id}").on('id -> id).as(task.single)
  } 

  def findTarea(id: Int): Option[String] = DB.withConnection { implicit c =>
    SQL("select label from task where id = {id}").on('id -> id).as(scalar[String].singleOpt)
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

  def createUser(user_name: String){
    DB.withConnection { implicit c =>
      SQL("insert into task_user(login) values ({user_name})").on(
        'user_name -> user_name
      ).executeUpdate()
    }
  }

  def all(user_name: String): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task where user_name = {user_name}").on('user_name -> user_name).as(task *)
  }

  def obtFecha(id: Int): Option[Date] = DB.withConnection { implicit c =>
    SQL("select task_date from task where id={id}").on('id -> id).as(scalar[Date].singleOpt)
  }

  def createFecha(id: Int, fecha: Date): Int = { 
    val value:Int = DB.withConnection { 
      implicit c => SQL("update task set task_date={fecha} where id={id}")
      .on('fecha -> fecha, 'id -> id).executeUpdate()
    }
    value
  }

  def createCategory(id: Int, category: String): Int = {
    val value:Int = DB.withConnection {
      implicit c => SQL("update task set category={category} where id={id}")
      .on('category -> category, 'id -> id).executeUpdate()
    }
    value
  }

  def createUserCategory(id: Int, user: String, category: String): Int = {
    val value:Int = DB.withConnection {
      implicit c => SQL("update task set category={category},user_name={user} where id={id}")
      .on('category -> category,'user ->user, 'id -> id).executeUpdate()
    }
    value
  }

  def getUserCategory(user: String, category: String): List[Task] = DB.withConnection{
    implicit c => SQL("select * from task where user_name={user} and category={category}")
    .on('user -> user, 'category -> category).as(task *)
  }

  def getCategory(category: String): List[Task] = DB.withConnection{
    implicit c => SQL("select * from task where category={category}").on('category -> category).as(task *)
  }
}
