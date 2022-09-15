package com.sample.productlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.productlist.datasource.Resource
import com.sample.productlist.datasource.local.LocalDataSource
import com.sample.productlist.datasource.local.database.entity.CommentEntity
import com.sample.productlist.datasource.local.database.entity.IssueEntity
import com.sample.productlist.datasource.remote.RemoteDataSource
import com.sample.productlist.datasource.remote.ResourceError
import com.sample.productlist.datasource.remote.model.ResourceDownloadedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteViewModel(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllIssues() {

        viewModelScope.launch {
            try {
                val response = remoteDataSource.getIssues()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    for (item in response.body()!!) {
                        var labels = ""
                        var labelsColor = ""
                        if (!item.labels.isNullOrEmpty()) {
                            labels = item.labels!![0].name.toString()
                            labelsColor = item.labels!![0].color.toString()
                        }
                        val issue = IssueEntity(
                            item.title.toString(),
                            item.number.toString(),
                            item.updated_at.toString(),
                            item.body.toString(),
                            item.user.login.toString(),
                            item.user.avatar_url.toString(),
                            labels, labelsColor,
                            item.comments_url.toString(),
                            item.state.toString()
                        )
                        localDataSource.insertIssue(issue)
                    }
                    progressLiveData.postValue(true)
                } else {
                    progressLiveData.postValue(false)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    progressLiveData.postValue(false)
                }
            }
        }
    }

    fun getComments(url: String) {

        viewModelScope.launch {
            try {
                val response = remoteDataSource.getComments(url)
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    for (item in response.body()!!) {

                        val comment = CommentEntity(
                            item.id.toString(),
                            item.updated_at.toString(),
                            item.body.toString(),
                            item.user.login.toString(),
                            item.user.avatar_url.toString()
                        )
                        localDataSource.insertComment(comment)
                    }
                    progressLiveData.postValue(true)
                } else {
                    progressLiveData.postValue(false)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    progressLiveData.postValue(false)
                }
            }
        }
    }

    fun downloadImage() {

    }
}
