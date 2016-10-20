package controllers

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

import com.google.inject.Inject
import com.google.inject.Singleton

import model.UserHasRepoDAO
import play.api.mvc.Action
import play.api.mvc.Controller
import services.DbShortestPathImp
import services.ShortestPathBSF
import services.ShortestPathGeneric
import play.api.libs.json._

@Singleton
class ApplicationController @Inject() (userRepo: UserHasRepoDAO, dbService: DbShortestPathImp)(implicit ec: ExecutionContext) extends Controller {

  val sp: ShortestPathGeneric = new ShortestPathBSF(dbService)

  def shortestPath(source: String, destination: String) = Action { implicit request =>
    //import that funky stuff of the api
    val hops = sp.calculateShortestPath(source, destination)
    Ok(Json.obj("hops" -> hops))
  }

}

