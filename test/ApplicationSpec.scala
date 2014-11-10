package test

import org.specs2.mutable._

import play.api.libs.json._
import play.api.test._  
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  import models._

  "Application" should {
    "creacion tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val tarea1 = route(FakeRequest.apply(POST, "/tasks").withJsonBody(Json.obj(
          "id" -> 1, "label" -> "Gel"))).get

        status(tarea1) must equalTo(CREATED)
      }
    }
    "error creacion tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val tarea1 = route(FakeRequest.apply(POST, "/tasks")).get

        status(tarea1) must equalTo(BAD_REQUEST)
      }
    }
    "error creacion tarea con usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val tarea1 = controllers.Application.addTask("anonimo")(FakeRequest())

        status(tarea1) must equalTo(BAD_REQUEST)

      }
    }
    "creacion de una tarea con usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val tarea1 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Jabon"))
        val result = controllers.Application.tasks(FakeRequest())

        status(tarea1) must equalTo(CREATED)
        status(result) must equalTo(OK)
        //redirectLocation(result) must equalTo(Some("/tasks"))

        contentAsString(result) must contain("Jabon")
      }
    }
    "consulta tarea no disponible" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        val result = controllers.Application.consultTask(1)(FakeRequest())

        status(result) must equalTo(NOT_FOUND)
      }
    }
    "consulta de una tarea disponible" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val tarea1 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Goma"))
        val result = controllers.Application.consultTask(1)(FakeRequest())

        status(result) must equalTo(OK)
        contentAsString(result) must contain("""{"id":1,"label":"Goma","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"}""")
      }
    }
    "listar tareas" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val tarea1 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Goma"))
        val tarea2 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "2", "label" -> "Pan"))
        val tarea3 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "3", "label" -> "Leche"))
        val result = controllers.Application.tasks(FakeRequest())

        status(result) must equalTo(OK)
        contentAsString(result) must contain("""[{"id":1,"label":"Goma","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"},{"id":2,"label":"Pan","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"},{"id":3,"label":"Leche","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"}]""")
      }      
    }
    "borrar tarea que no existe" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = route(FakeRequest.apply(DELETE, "/tasks/1")).get 

        status(result) must equalTo(NOT_FOUND)
        contentAsString(result) must contain("Error al borrar")
      }
    }
    "borrar tarea que existe" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val tarea1 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Pan"))
        val tarea2 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "2", "label" -> "Pera"))
        val tarea3 = controllers.Application.addTask("anonimo")(
          FakeRequest().withFormUrlEncodedBody("id" -> "3", "label" -> "Pomelo"))
        val result = controllers.Application.deleteTask(2)(FakeRequest())
        val ver = controllers.Application.tasks(FakeRequest())

        status(result) must equalTo(OK)
        contentAsString(result) must contain("Borrado")
        contentAsString(ver) must contain("""[{"id":1,"label":"Pan","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"},{"id":3,"label":"Pomelo","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"}]""")
      }
    }
    "error crear tarea a un usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      
        val tarea1 = route(FakeRequest.apply(POST, "/miguel/tasks")).get

        status(tarea1) must equalTo(BAD_REQUEST)
      }
    }
    "crear tarea a un usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val usuario1 = controllers.Application.addTask("miguel")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Kiwi"))

        status(usuario1) must equalTo(CREATED)
        contentAsString(usuario1) must contain("""Kiwi""")
      }
    }
    "obtener tareas de un usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val tarea1 = route(FakeRequest.apply(POST, "/pepe/tasks").withJsonBody(Json.obj(
          "id" -> 1, "label" -> "Gel", "user_name" -> "pepe"))).get
        val tarea2 = route(FakeRequest.apply(POST, "/pepe/tasks").withJsonBody(Json.obj(
          "id" -> 2, "label" -> "Champu", "user_name" -> "pepe"))).get

        val ver = route(FakeRequest.apply(GET, "/pepe/tasks")).get

        status(ver) must equalTo(OK)
        contentAsString(ver) must contain("""[{"id":1,"label":"Gel","user_name":"pepe","""
          +""""task_date":"NoData","category":"descatalogado"},{"id":2,"label":"Champu","user_name":"pepe","""
          +""""task_date":"NoData","category":"descatalogado"}]""")
      }
    }
    "error tarea a un usuario inexistente" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val tarea1 = route(FakeRequest.apply(POST, "/pepe/tasks").withJsonBody(Json.obj(
          "id" -> 1, "label" -> "Gel", "user_name" -> "pepe"))).get
        val tarea2 = route(FakeRequest.apply(POST, "/pepe/tasks").withJsonBody(Json.obj(
          "id" -> 2, "label" -> "Champu", "user_name" -> "pepe"))).get

        val usuario1 = route(FakeRequest.apply(GET, "/pepito/tasks")).get

        status(usuario1) must equalTo(NOT_FOUND)
        contentAsString(usuario1) must contain("Usuario no encontrado")
      }
    }
    "error tarea porque usuario no existe" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        val usuario1 = controllers.Application.addTask("pepito")(
          FakeRequest().withFormUrlEncodedBody("id" -> "1", "label" -> "Kiwi", "user_name"->"pepe"))

        status(usuario1) must equalTo(NOT_FOUND)
        contentAsString(usuario1) must contain("No podemos insertar tarea debido que no existe el Usuario")
      }
    }
    "crear fecha a una tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Pintura"))
        val fecha = route(FakeRequest.apply(POST, "/tasks/1/1997-05-17")).get
        val consult1 = controllers.Application.consultTask(1)(FakeRequest())

        status(fecha) must equalTo(OK)
        contentAsString(fecha) must contain("La fecha ha sido modificada")
        contentAsString(consult1) must contain("""{"id":1,"label":"Pintura","user_name":"anonimo","""
          +""""task_date":"1997-05-17","category":"descatalogado"}""")
      }
    }
    "error crear fecha a una tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Papel"))
        val fecha = route(FakeRequest.apply(POST, "/tasks/2/1997-05-24")).get

        status(fecha) must equalTo(NOT_FOUND)
        contentAsString(fecha) must contain("No podemos guardar fecha, tarea no existe")
      }
    }
    "obtener fecha de una tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Correr"))
        val fecha = route(FakeRequest.apply(POST, "/tasks/1/1997-03-08")).get
        val consulta = route(FakeRequest.apply(GET, "/tasks/1/date")).get

        status(consulta) must equalTo(OK)
        contentAsString(consulta) must contain("1997-03-08")
      }
    }
    "error obtener fecha de una tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Nadar"))
        val fecha = route(FakeRequest.apply(POST, "/tasks/1/1997-09-19")).get
        val consulta = route(FakeRequest.apply(GET, "/tasks/2/date")).get

        status(consulta) must equalTo(NOT_FOUND)
        contentAsString(consulta) must contain("La tarea no existe")
      }
    }
    "error2 obtener fecha de una tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Rodar"))
        val consulta = route(FakeRequest.apply(GET, "/tasks/1/date")).get

        status(consulta) must equalTo(NOT_FOUND)
        contentAsString(consulta) must contain("Fecha no encontrada")
      }
    }
    "registrar categoria de una tarea sin usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Naranja"))
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Papaya"))
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Calabaza"))

        val fruta1 = route(FakeRequest.apply(POST, "/tasks/1/date/fruta")).get
        val fruta2 = route(FakeRequest.apply(POST, "/tasks/2/date/fruta")).get
        val ver = route(FakeRequest.apply(GET, "/tasks")).get

        contentAsString(ver) must contain("""[{"id":1,"label":"Naranja","user_name":"anonimo","""
          +""""task_date":"NoData","category":"fruta"},{"id":2,"label":"Papaya","user_name":"anonimo","""
          +""""task_date":"NoData","category":"fruta"},{"id":3,"label":"Calabaza","user_name":"anonimo","""
          +""""task_date":"NoData","category":"descatalogado"}]""")
      }
    }
    "error registrar categoria de una tarea sin usuario" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Naranja"))
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Papaya"))
        controllers.Application.newTask()(
          FakeRequest().withFormUrlEncodedBody("label" -> "Calabaza"))

        val fruta1 = route(FakeRequest.apply(POST, "/tasks/4/date/fruta")).get

        status(fruta1) must equalTo(NOT_FOUND)
        contentAsString(fruta1) must equalTo("No podemos guardar categoria, tarea no existe")
      }
    }
  }   
}







