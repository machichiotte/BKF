package com.whitedev.bkf.ui.tableview

class MyTableViewModel {

    //todo modifier pour un type
    fun getCellItemViewType(column: Int): Int {

        return when (column) {
            4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ->
                // 5. column header is gender.
                CHECKBOX_TYPE
            else -> 0
        }
    }

    fun getCellItemViewTypeCb(type: String): Int {

        return when (type) {
            "CHECKBOX" -> CHECKBOX_TYPE
            else -> 0
        }
    }

    companion object {
        const val CHECKBOX_TYPE = 1
    }


}



