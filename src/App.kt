
/*
    2020-05-16
 */
import java.util.*
import kotlin.concurrent.schedule


fun main() {
    var board = Board() //new clean board
    val reader = Scanner(System.`in`)

    while(!board.isGameOver()) {
        println(board)
        print("Your Turn - Enter a valid number: ")
        val input:Int = reader.nextInt()
        board = board.makeMove(input)
        println(board)

        Timer().schedule(2000){}
        board = board.bestMove()
    }

}