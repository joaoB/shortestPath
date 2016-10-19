package model

import scala.concurrent.{ Future, ExecutionContext }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import com.google.inject._

case class User(id: Long, username: String)

@Singleton
class Users @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private class UserTableDef(tag: Tag) extends Table[User](tag, "contributor") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")

    override def * =
      (id, username) <> (User.tupled, User.unapply)
  }

  private val users = TableQuery[UserTableDef]

  def add(user: User): Future[Long] = {
    dbConfig.db.run((users returning users.map(_.id)) += user)
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(users.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[User]] = {
    dbConfig.db.run(users.result)
  }
  
  def update(userToUpdate: User) = {
    dbConfig.db.run(users.filter(_.id === userToUpdate.id).update(userToUpdate))
  }
  
}
