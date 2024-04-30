package com.example.task.data.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.repository.UserRepository
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val userRepository: UserRepository
) : PagingSource<Int, UserDomainModel>() {
    override fun getRefreshKey(state: PagingState<Int, UserDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDomainModel> {
        return try {
            val page = params.key ?: 0
            val responseList = userRepository.getUsers(page = page)
            val nextPageNumber = if (responseList.size < params.loadSize) null else page + 1
            val prevKeyNumber = if (page == 0) null else page - 1
            LoadResult.Page(
                data = responseList,
                prevKey = prevKeyNumber,
                nextKey = nextPageNumber
            )
        } catch (e: HttpException)  {
            LoadResult.Error(e)
        } catch (e : UnknownHostException) {
            LoadResult.Error(e)
        }
    }
}
