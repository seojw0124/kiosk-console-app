import model.UserInfo

class UserManager {

    private val userList = mutableListOf<UserInfo>()
    private var currenUser: UserInfo? = null

    fun saveUserInfo(user: UserInfo) {
        userList.add(user)
    }

    fun setCurrentUser(userId: Int): UserInfo {
        currenUser = userList.find { it.userId == userId }
        return currenUser!!
    }

    fun isUserListEmpty(): Boolean {
        return userList.isEmpty()
    }

    fun getCurrentUser(): UserInfo? {
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