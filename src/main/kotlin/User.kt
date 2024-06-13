import model.UserItem

class User {

    private val userList = mutableListOf<UserItem>()
    private var currenUser: UserItem? = null

    fun saveUserInfo(user: UserItem) {
        userList.add(user)
    }

    fun setCurrentUser(userId: Int): UserItem {
        currenUser = userList.find { it.userId == userId }
        return currenUser!!
    }

    fun getAllUserList(): MutableList<UserItem> {
        return userList
    }

    fun isUserListEmpty(): Boolean {
        return userList.isEmpty()
    }

    fun getCurrentUserId(): Int {
        return currenUser?.userId ?: 0
    }

    fun getCurrentUserName(): String {
        return currenUser?.userName ?: ""
    }

    fun getLastCurrentUserId(): Int {
        return userList.last().userId
    }
}