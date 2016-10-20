package model

import scala.concurrent.{ Future, ExecutionContext }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import com.google.inject._

case class UserHasRepo(id: Long, userId: Long, repoId: Long)

@Singleton
class UserHasRepos @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private class UserHasRepoTableDef(tag: Tag) extends Table[UserHasRepo](tag, "user_has_repo") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("contributorId")
    def repoId = column[Long]("repoId")
    
    override def * =
      (id, userId, repoId) <> (UserHasRepo.tupled, UserHasRepo.unapply)
  }

  private val usersRepos = TableQuery[UserHasRepoTableDef]

}
