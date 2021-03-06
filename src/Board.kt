import kotlin.math.*
/*
         Board is a 1D-Array with switching turns (+1 for Player X' Turn, -1 for Player O's Turn)
         0 = empty  ' '         -1 = Player 'O'             1 = Player 'X'
 */

class Board(private val fields: List<Int> = listOf(0,0,0,0,0,0,0,0,0), private val turn: Int = +1, private val history: List<Board> = listOf()) {

    fun makeMove(pos: Int) = Board(fields.take(pos) + listOf(turn) + fields.takeLast(fields.size - pos - 1), -turn, history.plus(this))

    fun undoMove() = history.last()

    fun bestMove(): Board = minimax(this).first

    fun isGameOver() = !threeInARow().isNullOrEmpty() || possibleMoves().isEmpty()

    //Returns 1 if playerX won, -1 if playerY won and 0 if it's a tie or the game is not over
    private fun result(): Int = if(threeInARow().isNullOrEmpty()) 0 else -turn

    private fun possibleMoves(): List<Board> = fields.mapIndexed { index, i -> if(i == 0) makeMove(index) else null }.filterNotNull()

    private fun winningMove(): Board? = possibleMoves().filter { it.result() == turn }.randomOrNull()

    //Returns: List with the winning row if there is one otherwise null
    private fun threeInARow(): List<Int>? {
        val rows = listOf(listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), listOf(0,4,8), listOf(2,4,6))
        rows.forEach { if(it.sumBy { i -> fields.elementAt(i)}.absoluteValue == 3) return it }
        return null
    }

    private fun minimax(board: Board): Pair<Board, Int> {
        val winner = board.winningMove()
        if(winner != null)
            return Pair(winner, winner.result())

        var bestOption = Pair(board, Int.MAX_VALUE * -board.turn)
        board.possibleMoves().forEach {
            val option = minimax(it)
            if(option.second * turn > bestOption.second * turn)
                bestOption = option
        }
        return bestOption
    }

    override fun toString(): String {
        var cnt = 1
        return fields.joinToString(separator = "|", prefix = "-------\n|", postfix = "-------",
                transform = { (if(it == -1) "O" else if(it == 1) "X" else " ") + (if(cnt++ % 3 == 0) "|\n" else "") })
    }
}