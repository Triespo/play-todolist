package test

import org.specs2.mutable._  
import play.api.test._  
import play.api.test.Helpers._

class ModelSpec extends Specification {

    import models._

    def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str  
    def strToDate(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

    "Models" should {
        "creacion tarea modelo" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

                Task.create("Arroz")
                val Some(ver) = Task.findTarea(1)

                ver must equalTo("Arroz")
            }
        }
        "consulta tarea modelo" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

                Task.create("Uva")
                Task.create("Naranja")
                Task.create("Limon")

                val ver1 = Task.consult(1)
                val ver2 = Task.consult(2)
                val ver3 = Task.consult(3)

                ver1.label must equalTo("Uva")
                ver2.label must equalTo("Naranja")
                ver3.label must equalTo("Limon")
            }
        }
        "listado tareas modelo" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

                Task.create("Cereza")
                Task.create("Melon")
                Task.create("Sandia")

                val todos = Task.all()
                val task1 = todos.head
                val task2 = todos.tail.head
                val task3 = todos.tail.tail.head

                task1.label must equalTo("Cereza")
                task2.label must equalTo("Melon")
                task3.label must equalTo("Sandia")
            }
        }
        "borrado tarea modelo" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())){

                Task.create("Harina")
                Task.create("Queso")

                val delete = Task.delete(2)

                delete must equalTo(1) //si >0 es que lo ha borrado
            }
        }
    }  
}