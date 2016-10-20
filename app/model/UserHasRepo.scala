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

  def getUsersByRepo(repo: String) = {
    val a = dbConfig.db.run(usersRepos.filter(_.repoId === repo).result)
    val b = a map (result => result map {
      _.userId
    })
    b
  }

  def getRepsOfNode(user: String) = {
    val a = dbConfig.db.run(usersRepos.withFilter(_.userId === user).result)
    val b = a map (result => result map {
      _.repoId
    })
    b
  }

}

