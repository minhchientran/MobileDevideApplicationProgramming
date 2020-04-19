class Series (input: String){

    // TODO: Implement proper constructor
    var series = input
    init {
        var digit = "^[0-9]*$".toRegex()
        if(!digit.matches(series)){
            throw IllegalArgumentException()
        }
    }
    fun getLargestProduct(span: Int): Long {
        //TODO("Implement this function to complete the task")
        if (span > 0 && series.isEmpty()) {
            throw IllegalArgumentException()
        }
        if (span > series.length || span < 0) {
            throw IllegalArgumentException()
        }
        var largestProduct : Long = 0
        for (i in 0 .. (series.length - span)) {
            var tempProduct : Long = 1
            for (j in 0 until span) {
                tempProduct *= series[i+j].toString().toInt()
            }
            if (tempProduct > largestProduct) {
                largestProduct = tempProduct
            }
        }
        return largestProduct
    }
}