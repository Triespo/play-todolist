package test

import org.specs2.mutable._

import play.api.test._  
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in {  
      running(FakeApplication()) {  
        route(FakeRequest(GET, "/boum")) must beNone  
      }
    }

    "render the index page" in {  
      running(FakeApplication()) {

        val Some(home) = route(FakeRequest(GET, "/tasks/login"))

        status(home) must equalTo(OK)  
        contentType(home) must beSome.which(_ == "text/html")  
        contentAsString(home) must contain ("Login")  
      }
    }

    "process correct login" in {  
      running(FakeApplication()) {

        val Some(result) = route(  
          FakeRequest(POST, "/tasks/login").withFormUrlEncodedBody(("email","pepito@gmail.com"),("password","1234"))  
          )

        status(result) must equalTo(SEE_OTHER)  
        redirectLocation(result) must equalTo(Some("/tasks"))  
        session(result).apply("email") must equalTo("pepito@gmail.com")  
      }      
    }  
  }   
}