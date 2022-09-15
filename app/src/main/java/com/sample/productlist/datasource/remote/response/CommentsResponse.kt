package com.sample.productlist.datasource.remote.response

data class CommentsResponse(
    var id: String? = null,
    var body: String? = null,
    var updated_at: String? = null,
    var user: User
) {
}