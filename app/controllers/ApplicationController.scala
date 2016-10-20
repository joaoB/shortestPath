package controllers

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject.Inject
import com.google.inject.Singleton

import model.UserHasRepoDAO
import play.api.mvc.Action
import play.api.mvc.Controller
import services.DbShortestPathImp
import services.ShortestPathBSF
import services.ShortestPathGeneric

@Singleton
class ApplicationController @Inject() (userRepo: UserHasRepoDAO, dbService: DbShortestPathImp)(implicit ec: ExecutionContext) extends Controller {

  val sp: ShortestPathGeneric = new ShortestPathBSF(dbService)

  def index = Action.async { implicit request =>
    //Ok(views.html.index(users))
    Future(Ok(sp.calculateShortestPath("a", "c", Set()).toString))

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

}

