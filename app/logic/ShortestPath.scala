package logic

abstract class ShortestPathGeneric {
  object NonEmpty {
    def unapply(l: List[Int]) = l.headOption.map(_ => l)
  }
  
  def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int
}

class ShortestPathBSF extends ShortestPathGeneric {

  override def calculateShortestPath(cur: String, dest: String, visited: Set[String]): Int = {
    0
  }

}

object ShortestPath extends ShortestPathBSF