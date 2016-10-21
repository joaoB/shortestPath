package services

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.annotation.tailrec

abstract class ShortestPathGeneric(dbShortestPath: DbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int
}

class ShortestPathBSF(dbShortestPath: DbShortestPath) extends ShortestPathGeneric(dbShortestPath) {
  def calculateShortestPath(cur: String, dest: String): Int = _calculateShortestPath(Set(cur), dest, List(cur), 0)

  @tailrec
  private def _calculateShortestPath(toVisit: Set[String], dest: String, visited: List[String], count: Int): Int = {
    if (toVisit.contains(dest)) count //reached the final node
    else {
      val neighbours = toVisit.par.map( node =>
        Await.result(dbShortestPath.getNeighboursOfUser(node), Duration.Inf))
      val next = neighbours.flatten.filter(n => !visited.contains(n))
      if (next.isEmpty) -1 // impossible way to the destination source
      else _calculateShortestPath(next.toList.toSet, dest, next.toList ++ visited.toList, count + 1) //recursive find more nodes
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
