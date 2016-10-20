package logic

import scala.concurrent.ExecutionContext.Implicits.global

abstract class ShortestPathGeneric(dbShortestPath: DbShortestPath) {
  object NonEmpty {
    def unapply(l: List[Int]) = l.headOption.map(_ => l)
  }

  //def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int

  def getRepsOfUser(user: String) = dbShortestPath.getRepsOfUser(user)

}

class ShortestPathBSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {

  import scala.concurrent.Future

/*  override def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Future[Int] = {
    val newVisited = visited + cur
    if (cur == dest) Future.successful(0)
    else {
      //val currentNodeReps = db(cur)

      dbShortestPath.getRepsOfUser(cur).map {
        currentNodeReps =>
          val neighboursOfCur = currentNodeReps flatMap (getUsersByRepo)
          val nexts = neighboursOfCur.filter(n => !visited.contains(n) && currentNodeReps.exists(s => db(n) exists (_ contains s)))
          val shorts = nexts map (1 + shortestPath(_, dest, newVisited))
          shorts match {
            case NonEmpty(l) => l.min
            case _           => -1 //uuupppsss you are not connected to that user
          }
      }

    }
  }*/

}

//object ShortestPath extends ShortestPathBSF