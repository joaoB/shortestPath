package dbMocked

import scala.annotation.migration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import services.DbShortestPath

class ShortestPathServiceSpec

class DbShortestMocked(db: Map[String, List[String]]) extends DbShortestPath {

  override def getNeighboursOfUser(user: String): Future[Seq[String]] = {
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
}