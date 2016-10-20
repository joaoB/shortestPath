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

  def shortestPath(source: String, destination: String) = Action { implicit request =>
    val hops = sp.calculateShortestPath(source, destination)
    Ok(Json.obj("hops" -> hops))
  }

}

