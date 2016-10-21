package services

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ShortestPathGeneric(dbShortestPath: DbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int
}

//TODO: if cur == dest, but if cur does not exist, it should return -1
class ShortestPathBSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int = _calculateShortestPath(Set(cur), dest, List(cur), 0)

  private def _calculateShortestPath(toVisit: Set[String], dest: String, visited: List[String], count: Int): Int = {

    if (toVisit.contains(dest)) count //reached the final node
    else {
      //TODO: all calls in parallel would be sweeeet
      val neighbours = for (n <- toVisit) yield Await.result(dbShortestPath.getNeighboursOfUser(n), Duration.Inf)
      val next = neighbours.flatten.filter(n => !visited.contains(n))
      if (next.isEmpty) -1 // impossible way to the destination source
      else _calculateShortestPath(next, dest, next.toList ++ visited.toList, count + 1) //recursive find more nodes
    }
  }
}

class ShortestPathDSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int = _calculateShortestPath(cur, dest, Set())
  private def _calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int = {
    val newVisited = visited + cur
    if (cur == dest) 0
    else {
      val neighbours = Await.result(dbShortestPath.getNeighboursOfUser(cur), Duration.Inf)
      val next = neighbours.distinct.filter(n => !visited.contains(n))
      val pathsValues = for (n <- next) yield 1 + _calculateShortestPath(n, dest, newVisited)
      if (!pathsValues.isEmpty) pathsValues.min else -1
    }
  }

}
