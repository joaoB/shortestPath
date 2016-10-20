package controllers

import scala.concurrent.ExecutionContext

import com.google.inject.Inject
import com.google.inject.Singleton

import model.UserHasRepoDAO
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Action
import play.api.mvc.Controller
import services.DbShortestPathImp
import services.ShortestPathBSF
import services.ShortestPathGeneric

@Singleton
class ApplicationController @Inject() (userRepo: UserHasRepoDAO, dbService: DbShortestPathImp)(implicit ec: ExecutionContext) extends Controller {

  val sp: ShortestPathGeneric = new ShortestPathBSF(dbService)

  def index = Action {
    implicit request =>
      {
        val message = "This is the index page of Curve ShortestPath Challenge!" +
          " <br><br> You can perform a GET request to /shortestPath/user1/user2" +
          " to know the shortest contribution path between the two users. " +
          " <br><br>return codes:<br><br> -1 -> one of the users does not exists on the database" +
          " <br><br> 0 -> users are the same" +
          " <br><br> i if i > 0 -> shortestPath between the two users"
        Ok(message).as(HTML)
      }
  }

  def shortestPath(source: String, destination: String) = Action { implicit request =>
    val hops = sp.calculateShortestPath(source, destination)
    Ok(Json.obj("hops" -> hops))
  }

}

