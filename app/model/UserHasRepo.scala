package model

import scala.concurrent.{ Future, ExecutionContext }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.driver.JdbcProfile
import com.google.inject._
import play.api.data.Forms._
import play.api.db.slick.{ HasDatabaseConfigProvider, DatabaseConfigProvider }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.util.Success

case class UserHasRepo(userId: String, repoId: String)

class UserHasRepos(tag: Tag) extends Table[UserHasRepo](tag, "contributor_has_repo") {

  def userId = column[String]("contributorId")
  def repoId = column[String]("repoId")

  override def * =
    (userId, repoId) <> (UserHasRepo.tupled, UserHasRepo.unapply)
}

@Singleton
class UserHasRepoDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private val usersRepos = TableQuery[UserHasRepos]

  def getUsersByRepo(repo: List[String]): Future[Seq[String]] = {
    getUsersByRepoAux(repo).map {
      result => result.filter(elem => repo.contains(elem.repoId)).map(_.userId)
    }
  }

  private def getUsersByRepoAux(repo: List[String]): Future[Seq[UserHasRepo]] = {
    dbConfig.db.run(usersRepos.result)
  }

  def getRepsOfNode(user: String) = {
    val a = dbConfig.db.run(usersRepos.withFilter(_.userId === user).result)
    val b = a map (result => result map {
      _.repoId
    })
    b
  }

  //given an user, find all users that he is connected with
  //get all nodes who share same projects
  def getNeighboursOfUser(user: String): Future[Seq[String]] = {
    for {
      repos <- getRepsOfNode(user)
      neighbours <- getUsersByRepo(repos toList)
    } yield neighbours
  }

}

