package com.whitedev.bkf.ui.tableview.model

/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import com.evrencoskun.tableview.filter.IFilterableModel
import com.evrencoskun.tableview.sort.ISortableModel

/**
 * Created by evrencoskun on 11/06/2017.
 */

open class CellModel : ISortableModel, IFilterableModel {

    private var mId: String? = null
    private var mColumn: String? = null
    var data: Any? = null
        private set
    var filterKeyword: String? = null

    constructor(id: String?) {
        this.mId = id
    }

    constructor(id: String?, data: Any?) {
        this.mId = id
        this.data = data
        this.filterKeyword = data.toString()
    }
    constructor(id: String?, data: Any?, column:String?) {
        this.mId = id
        this.data = data
        this.mColumn = column
        this.filterKeyword = data.toString()
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    override fun getId(): String? {
        return mId
    }

    fun getColumn(): String? {
        return mColumn
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    override fun getContent(): Any? {
        return data
    }

    fun setData(data: String) {
        this.data = data
    }

    override fun getFilterableKeyword(): String? {
        return filterKeyword
    }
}
