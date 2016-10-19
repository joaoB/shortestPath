package user

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration

import play.api.Application
import play.api.test.PlaySpecification
import play.api.test.WithApplication
import service.UserService

class ShortestPathServiceSpec extends PlaySpecification {

  "ShortestPath Service" should {

    def userService(implicit app: Application) = Application.instanceCache[UserService].apply(app)

    "validate reset times" in new WithApplication() {
      //val result = await(userService.resetUserTimes(11))
      // Assert.assertTrue(result.contains("reset"))
    }

  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)
}