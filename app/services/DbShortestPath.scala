package services

import scala.concurrent.Future

import com.google.inject.Inject
import com.google.inject.Singleton

import model.UserHasRepoDAO
import scala.concurrent.duration.Duration
import play.api.cache.CacheApi

trait DbShortestPath {
  def getNeighboursOfUser(user: String): Future[Seq[String]]
}

class DbShortestPathImp @Inject() (usersRepo: UserHasRepoDAO, cache: CacheApi) extends DbShortestPath {

  override def getNeighboursOfUser(user: String): Future[Seq[String]] = {
    cache.getOrElse[Future[Seq[String]]](user, Duration.Inf){usersRepo.getNeighboursOfUser(user)}
  }

}