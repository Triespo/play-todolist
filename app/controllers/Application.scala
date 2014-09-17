package controllers

import play.api._
import play.api.mvc._
import models.Task
import play.api.data._
import play.api.data.Forms._
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Application extends Controller {

  val task = {
    get[Long]("id") ~ 
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  val taskForm = Form(
  "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
   taskForm.bindFromRequest.fold(
     errors => BadRequest(views.html.index(Task.all(), errors)),
     label => {
       Task.create(label)
       Redirect(routes.Application.tasks)
     }
   )
  }

  def deleteTask(id: Long) = TODO

}