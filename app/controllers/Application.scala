package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.Task
import play.api.data._
import play.api.data.Forms._
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat

object Application extends Controller {

   val formato = new SimpleDateFormat("yyyy/MM/dd")

  implicit val taskWrites: Writes[Task] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "label").write[String] and
    (JsPath \ "user_name").write[String] and
    (JsPath \ "task_date".write[String].contramap[Option[Date]](data =>
      if(data != None)
        formato.format(data.getOrElse(data))
      else
        "NoData"
    )
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

  def consultTask(id: Int) = Action { 
    try{
      val json = Json.toJson(Task.consult(id))
      Ok(json)
    }catch{
      case e: Exception => NotFound("Error al consultar")   
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

  def deleteTask(id: Int) = Action {

    if(Task.delete(id) > 0)
      Ok("Borrado")
    else 
      NotFound("Error al borrar")
  }

   def userTasks(user: String) = Action {
      
      val encontrado = Task.findUser(user)

      if(encontrado != None){
        if(encontrado.getOrElse(user) == user){
          val json = Json.toJson(Task.all(user))
          Ok(json)
        }
        else
          NotFound("Usuario no existe")
      }
      else
        NotFound("Usuario no encontrado")
   }

   def addTask(userName: String) = Action { implicit request =>
      taskForm.bindFromRequest.fold(
        errors => BadRequest(views.html.index(Task.all(), errors)),
        label => {
          val userFound = Task.findUser(userName)
          if(userFound != None){
              Task.createInUser(userName, label)
              Created(Json.toJson(label))
          }
          else 
            NotFound("No podemos insertar tarea debido que no existe")
        }
      )
   }
}