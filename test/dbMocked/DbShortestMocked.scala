package dbMocked

import services.DbShortestPath
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.test.PlaySpecification
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import play.api.Application
import play.api.test.PlaySpecification
import play.api.test.WithApplication
import services.ShortestPathBSF
import junit.framework.Assert

class ShortestPathServiceSpec

class DbShortestMocked(db: Map[String, List[String]]) extends DbShortestPath {

  def getNeighboursOfUser(user: String): Future[Seq[String]] = {
    if (db.contains(user)) {
      val userRepos = db(user)
      val response = db.filter {
        pairs =>
          userRepos exists (s => pairs._2 exists (_ contains s))
      }.keys
      Future(response toSeq)
    } else {
      Future(Seq())
    }
  }

  def getRepsOfUser(name: String): Future[Seq[String]] = Future(db(name) toSeq)

}