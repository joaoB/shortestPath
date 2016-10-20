package services

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ShortestPathGeneric(dbShortestPath: DbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int
}

//TODO: if cur == dest, but if cur does not exist, it should return -1
class ShortestPathBSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int = _calculateShortestPath(cur, dest, Set())

  private def _calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int = {
    val newVisited = visited + cur
    if (cur == dest) 0
    else {
      val neighbours = Await.result(dbShortestPath.getNeighboursOfUser(cur), Duration.Inf)
      val next = neighbours.filter(n => !visited.contains(n))
      val pathsValues = for (n <- next) yield 1 + _calculateShortestPath(n, dest, newVisited)
      if (!pathsValues.isEmpty) pathsValues.min else -1
    }
  }
}
