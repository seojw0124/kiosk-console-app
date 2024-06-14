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

    fun isUserListEmpty(): Boolean {
        return userList.isEmpty()
    }

    fun getCurrentUser(): UserItem? {
        return currenUser
    }

    fun getCurrentUserId(): Int {
        return currenUser?.userId ?: 0
    }

    fun getCurrentUserName(): String {
        return currenUser?.userName ?: ""
    }

    fun getLastUserId(): Int {
        return userList.last().userId
    }
}