
/*
    2020-05-16
 */
import java.util.*
import kotlin.concurrent.schedule

fun example() {
    var board = Board(listOf(0,0,0,0,0,0,0,0,0)) //new clean board
    val reader = Scanner(System.`in`)

    while(!board.isGameOver()) {
        println(board)
        print("Your Turn - Enter a valid number (1-9): ")
        val input:Int = reader.nextInt()
        board = board.makeMove(input - 1)
        println(board)

        board = board.bestMove()
    }
    println(board)
}

fun main() {
    example()
}