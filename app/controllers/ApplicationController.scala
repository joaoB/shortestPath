package controllers

import model.User
import play.api.mvc._
import scala.concurrent.Future
import service.UserService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext
import com.google.inject._

@Singleton
class ApplicationController @Inject() (userService: UserService)(implicit ec: ExecutionContext) extends Controller {

  def index = Action.async { implicit request =>
    userService.listAllUsers map { users =>
      Ok(views.html.index(users))
    }
  }

  def shortestPath(source: String, destination: String) = Action.async { implicit request =>
    //import that funky stuff of the api
    Future(Ok("ok"))
  }

  def addUser() = Action.async { implicit request =>
    /*
      val newUser = User(0, "a", 0, 0, 1, 1)
    	userService.addUser(newUser).map(res =>
      Redirect(routes.ApplicationController.index()))
		*/
    Future(Ok("not implemented"))
  }

  def deleteUser(id: Long) = Action.async { implicit request =>
    userService.deleteUser(id) map { res =>
      Redirect(routes.ApplicationController.index())
    }
  }

}

