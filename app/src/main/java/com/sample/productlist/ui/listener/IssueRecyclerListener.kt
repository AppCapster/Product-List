package com.sample.productlist.ui.listener

import com.sample.productlist.datasource.local.database.entity.IssueEntity

interface IssueRecyclerListener {
    fun clickIssue(issue: IssueEntity)
}