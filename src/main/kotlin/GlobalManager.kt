import model.UserInfo

data class GlobalManager(
    val userManager: UserManager,
    val categoryManager: CategoryManager,
    val menuManager: MenuManager,
    val cartManager: CartManager,
    val orderManager: OrderManager,
    var currentUser: UserInfo? = null
)