package model

import java.sql.Timestamp

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.google.inject._

import play.api.data.Forms._
import play.api.db.slick.{ HasDatabaseConfigProvider, DatabaseConfigProvider }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import java.util.Calendar

case class Repo(id: Long, name: String)

class Repos(tag: Tag) extends Table[Repo](tag, "repo") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  override def * = (id, name) <> (Repo.tupled, Repo.unapply)
}

@Singleton
class ReposDAO @Inject() (dbConfigProvider: DatabaseConfigProvider) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import driver.api._

  val repos = TableQuery[Repos]

  def getNamesByIds(ids: List[Long]): Future[Seq[Repo]] = {
    val filtered = repos.withFilter { repo => ids.contains(repo.id) }
    dbConfig.db.run(filtered.result)
  }

  //we allow different repos to have the same name, but we are treating them as the same.....
  def getIdByName(name: String): Future[Option[Repo]] = {
    dbConfig.db.run(repos.filter(_.name === name).result.headOption)
  }
}

