package test

import org.specs2.mutable._

import play.api.test._  
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {
    "error creacion tarea" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val tarea1 = controllers.Application.addTask("anonimo")(FakeRequest())

        status(tarea1) must equalTo(BAD_REQUEST)

      }
    }
    "creacion de una tarea" in{
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
          +""""task_date":"NoData"}""")
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
          +""""task_date":"NoData"},{"id":2,"label":"Pan","user_name":"anonimo","""
          +""""task_date":"NoData"},{"id":3,"label":"Leche","user_name":"anonimo","""
          +""""task_date":"NoData"}]""")
      }      
    }
  }   
}







