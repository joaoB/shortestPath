package shortestPath

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import play.api.Application
import play.api.test.PlaySpecification
import play.api.test.WithApplication
import services.ShortestPathBSF
import junit.framework.Assert
import dbMocked.DbShortestMocked

class ShortestPathServiceSpec extends PlaySpecification {

  "ShortestPath Service -- validate happy cases" should {

    "validate shortest path logic :: source connected with end" in new WithApplication() {

      val reps1 = "a" :: Nil
      val reps2 = "a" :: "c" :: Nil

      val db =
        Map(("user1", reps1),
          ("user2", reps2))

      val service = new ShortestPathBSF(new DbShortestMocked(db))
      var cur = "user1"
      var dest = "user2"
      val result = service.calculateShortestPath(cur, dest, Set())
      Assert.assertTrue(result == 1)
    }

    "validate shortest path logic :: double path" in new WithApplication() {

      val reps1 = "a" :: "b" :: Nil
      val reps2 = "a" :: "c" :: Nil
      val reps3 = "a" :: "b" :: "c" :: "d" :: Nil
      val reps4 = "d" :: Nil

      val db =
        Map(("user1", reps1),
          ("user2", reps2),
          ("user3", reps3),
          ("user4", reps4))

      val service = new ShortestPathBSF(new DbShortestMocked(db))
      var cur = "user1"
      var dest = "user4"
      val result = service.calculateShortestPath(cur, dest, Set())
      Assert.assertTrue(result == 2)
    }

  }

  "ShortestPath Service -- validate simple error cases" should {
    val reps1 = "a" :: Nil
    val reps2 = "a" :: "c" :: Nil
    val reps3 = "d" :: Nil
    val reps4 = "a" :: "b" :: "d" :: Nil

    val db =
      Map(("user1", reps1),
        ("user2", reps2),
        ("user3", reps3),
        ("user4", reps4))

    "validate shortest path logic :: same user" in new WithApplication() {
      val service = new ShortestPathBSF(new DbShortestMocked(db))
      var cur = "user1"
      var dest = "user1"
      val result = service.calculateShortestPath(cur, dest, Set())
      Assert.assertTrue(result == 0)
    }

    "validate shortest path logic :: invalid user" in new WithApplication() {
      val service = new ShortestPathBSF(new DbShortestMocked(db))
      var cur = "invalidUser"
      var dest = "alsoInvalid"
      val result = service.calculateShortestPath(cur, dest, Set())
      Assert.assertTrue(result == -1)
    }

  }

  // def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)
}