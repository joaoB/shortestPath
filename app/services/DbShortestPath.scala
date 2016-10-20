package services

import com.google.inject.Inject
import com.google.inject.Singleton

import model.UserHasRepoDAO
import scala.concurrent.Future

trait DbShortestPath {
  def getRepsOfUser(name: String): Future[Seq[String]]
  def getNeighboursOfUser(user: String): Future[Seq[String]]
}

class DbShortestPathImp @Inject() (usersRepo: UserHasRepoDAO) extends DbShortestPath {

  override def getRepsOfUser(name: String): Future[Seq[String]] = {
    usersRepo.getRepsOfNode(name)
  }

  override def getNeighboursOfUser(user: String): Future[Seq[String]] = {
    usersRepo.getNeighboursOfUser(user)
  }

}