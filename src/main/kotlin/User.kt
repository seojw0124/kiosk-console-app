import model.UserItem

class User {

    private val userList = mutableListOf<UserItem>()
    private var currenUser: UserItem? = null

    fun saveUserInfo(user: UserItem) {
        userList.add(user)
    }

    fun setCurrentUser(userId: Int) {
        currenUser = userList.find { it.userId == userId }
    }

    fun getUserList(): MutableList<UserItem> {
        return userList
    }

    fun getCurrentUser(): UserItem? {
        return currenUser
    }

    fun getCurrentUserName(): String {
        return currenUser?.userName ?: ""
    }

    fun getLastCurrentUserId(): Int {
        return userList.last().userId
    }
}