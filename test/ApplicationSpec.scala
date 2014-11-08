package test

import org.specs2.mutable._

import play.api.test._  
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {
    
    "consulta tarea no disponible" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        
        val result = controllers.Application.consultTask(1)(FakeRequest())

        status(result) must equalTo(NOT_FOUND)
      }
    }
    /*"consulta de tarea disponible" in{
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        controllers.Application.addTask("Jabon")

        contentAsString(result) must contain("Jabon")
      }
    }*/
  }   
}