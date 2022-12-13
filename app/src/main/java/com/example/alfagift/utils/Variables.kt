package com.example.alfagift.utils

object Variables {

    val mainDirectory = "w174rd"

    object dateFormat {
        internal val _01_JANUARI_2019 = "dd MMMM yyyy"
        internal val _1_JANUARI_2019 = "d MMMM yyyy"
        internal val _2019_01_30 = "yyyy-MM-dd"
        internal val _01_JAN_koma_01_dot_30 = "dd MMM, HH.mm"
        internal val _01_JAN_2020_dot_01_30 = "dd MMM yyyy . HH:mm"
        internal val _01_JAN = "dd MMM"
        internal val _01_dot_30 = "HH.mm"
        internal val _01_30_50 = "HH:mm:ss"
        internal val _20019_01_01T01_30_50 = "yyyy-MM-dd'T'HH:mm:ss"
    }
}

typealias OnProgressBackPressed = (() -> Unit)
typealias OnClickCloseDialog = (() -> Unit)
typealias OnClickButtonDialog = (() -> Unit)