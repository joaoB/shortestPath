package services

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ShortestPathGeneric(dbShortestPath: DbShortestPath) {
  def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int
}

class ShortestPathBSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {
  def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int = {
    val newVisited = visited + cur
    if (cur == dest) 0
    else {
      val neighbours = Await.result(dbShortestPath.getNeighboursOfUser(cur), Duration.Inf)
      val next = neighbours.filter(n => !visited.contains(n))
      val pathsValues = for (n <- next) yield 1 + calculateShortestPath(n, dest, newVisited)
      if (!pathsValues.isEmpty) pathsValues.min else -1
    }
  }
}
