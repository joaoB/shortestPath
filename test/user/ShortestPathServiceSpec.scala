package user

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration

import logic.ShortestPathBSF
import play.api.Application
import play.api.test.PlaySpecification
import play.api.test.WithApplication

class ShortestPathServiceSpec extends PlaySpecification {

  "ShortestPath Service" should {

    def service(implicit app: Application) = Application.instanceCache[ShortestPathBSF].apply(app)

    "validate shortest path logic" in new WithApplication() {
      var cur = "user1"
      var dest = "user1"
      //val result = service.calculateShortestPath(cur, dest, Set())
      //Assert.assertTrue(result == 0)
    }

  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)
}