package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.Task
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "label").write[String]
  )(unlift(Task.unapply))

  val taskForm = Form(
  "label" -> nonEmptyText
  )

  def index = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def tasks = Action {
    val json = Json.toJson(Task.all())
    Ok(json)
  }

  def consultTask(id: Long) = Action { 
    try{
      val json = Json.toJson(Task.consult(id))
      Ok(json)
    }catch{
      case e: Exception => NotFound("Error")   
    }
  }

  def newTask = Action { implicit request =>
   taskForm.bindFromRequest.fold(
     errors => BadRequest(views.html.index(Task.all(), errors)),
     label => {
       Task.create(label)
       Created(Json.toJson(label))
     }
   )
  }

  def deleteTask(id: Long) = Action {

    if(Task.delete(id) > 0)
      Ok("Deleted")
    else 
      NotFound("Error")
  }

   def userTasks(user: String) = Action {
      if(User.FindUser(user)){
        val json = Json.toJson(User.all())
        Ok(json)
      }
      else
        NotFound("User not found")
   }

   def addTask(user:String) = Action { implicit request =>
      taskForm.bindFromRequest.fold(
        label => {
          User.create(user, label)
          Created(Json.toJson(label))
        }
      )
   }
}