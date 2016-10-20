package user

import scala.concurrent.Future

import org.scalatest._
import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import service.UserService
import controllers.ApplicationController
import scala.concurrent.ExecutionContext.Implicits.global
import service.UserService
import com.google.inject._
import play.api.http.Writeable
import play.api.inject.guice.GuiceApplicationBuilder

class ShortestPathControllerSpec extends PlaySpecification with Results {
  "End point is working#index" should {

    lazy val app = new GuiceApplicationBuilder().build
    val basicHeaders = Headers(
      "type" -> "application/json")
    def routeGET(uri: String) = getRoute(GET, uri, AnyContentAsEmpty)
    def getRoute[A](method: String, uri: String, body: A)(implicit w: Writeable[A]) = route(app, FakeRequest(method, uri, basicHeaders, body)).get

    def codeMustMatch(code: Int, result: Future[Result]) = {
      status(result) must equalTo(code)
    }

    s"End point is available" in {
      val source = "source_user"
      val destination = "destination_user"
      codeMustMatch(200, routeGET("/shortestPath/source_user/destination_user"))
    }
  }
}
