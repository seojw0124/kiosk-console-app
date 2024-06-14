import model.UserInfo

data class GlobalManager(
    val categoryManager: CategoryManager,
    val menuManager: MenuManager,
    val cartManager: CartManager,
    val orderManager: OrderManager,
)