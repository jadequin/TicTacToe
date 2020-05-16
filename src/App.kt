







fun main() {
    var board = Board() //new clean board

    //com player against com-player until end
    while(!board.isGameOver()) {
        board = board.makeBestMove()
        println(board)
    }
}