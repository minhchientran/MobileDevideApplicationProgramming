object Hamming {

    fun compute(leftStrand: String, rightStrand: String): Int {
        //TODO("Implement this function to complete the task")
        if (leftStrand.length > rightStrand.length || leftStrand.length < rightStrand.length) {
            throw IllegalArgumentException("left and right strands must be of equal length")
        }
        var difference = 0
        for (index in leftStrand.indices) {
            if (leftStrand[index] != rightStrand[index]) {
                difference++
            }
        }
        return difference
    }
}
