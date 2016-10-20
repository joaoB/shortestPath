package logic

import scala.concurrent.ExecutionContext.Implicits.global

import com.google.inject.Inject

import scala.concurrent.Future

trait DbShortestPath {
  def getRepsOfUser(name: String): Future[Seq[String]]
  def getUsersByRepo(repo: String): Future[Seq[String]]
}

//TODO: refractor this using foreign keys!!!!
/*class DbShortestPathImp @Inject() () extends DbShortestPath {
  override def getRepsOfUser(name: String): Future[Seq[String]] = {
    userService.getByName(name).flatMap {
      elem =>
        elem match {
          case Some(user) => repoService.getNamesByIds(user.id)
          case None       => Future(Seq())
        }
    }
  }

  override def getUsersByRepo(repo: String): Future[Seq[String]] = {
    //get repo id
    //get all users from userhasrepo that have that repoId
    val repoId = repoService.getIdByName(repo)
    
  }
}*/