package gutierrez.ruben.thecheezery_gutierrezruben

data class Product(
    val name: String,
    val price: Double,
    val imageName: String,
    val description: String,
    val category: String
)

object ProductCatalog {
    val categories = listOf("Hot drinks", "Cold drinks", "Salties", "Sweets")

    val products = listOf(
        Product("Latte", 6.0, "latte", "Coffee drink made with espresso and steamed milk.", "Hot drinks"),
        Product("Hot chocolate", 5.0, "hotchocolate", "Heated chocolate drink topped with marshmallows.", "Hot drinks"),
        Product("Espresso", 4.0, "espresso", "A full-flavored, concentrated form of coffee.", "Hot drinks"),
        Product("Chai Latte", 6.0, "chailatte", "Spiced tea concentrate blended with milk.", "Hot drinks"),
        Product("Cappuccino", 7.0, "capuccino", "Espresso topped with steamed milk foam.", "Hot drinks"),
        Product("Americano", 2.0, "americano", "Espresso softened with hot water.", "Hot drinks"),
        Product("Caramel Frap", 5.0, "caramel_frap", "Coffee, milk and ice with buttery caramel and whipped cream.", "Cold drinks"),
        Product("Chocolate Frap", 6.0, "chocolate_frap", "Mocha sauce, chocolate chips, milk and ice blended together.", "Cold drinks"),
        Product("Cold Brew", 3.0, "coldbrew", "Coffee steeped in cool water for a smooth, rich flavor.", "Cold drinks"),
        Product("Matcha Latte", 4.0, "matcha", "Matcha green tea powder with creamy milk and a little sugar.", "Cold drinks"),
        Product("Oreo Milkshake", 7.0, "oreomilkshake", "Chocolate ice cream and Oreo cookies topped with whipped cream.", "Cold drinks"),
        Product("Peanut Milkshake", 7.0, "peanutmilkshake", "Vanilla ice cream blended with peanut butter and chocolate.", "Cold drinks"),
        Product("Chicken Crepes", 6.0, "chickencrepes", "Fine crepes stuffed with Alfredo chicken, spinach and mushrooms.", "Salties"),
        Product("Club Sandwich", 5.0, "clubsandwich", "A delicious sandwich served with french fries.", "Salties"),
        Product("Panini", 4.0, "ic_panini", "Italian bread sandwich, warmed by grilling.", "Salties"),
        Product("Philly Cheese Steak", 6.0, "phillycheesesteak", "Grilled onions, peppers, mushrooms and Provolone.", "Salties"),
        Product("Nachos", 7.0, "nachos", "Tortilla chips with beef, melted cheddar and fresh toppings.", "Salties"),
        Product("Blueberry Cake", 6.0, "blueberrycake", "Vanilla cake with cream cheese topping and blueberries.", "Sweets"),
        Product("Chocolate Cupcake", 3.0, "chocolatecupcake", "Chocolate cupcake topped with buttercream.", "Sweets"),
        Product("Lemon Tartalette", 4.0, "lemontartalette", "Pastry shell with a bright lemon filling.", "Sweets"),
        Product("Red Velvet Cake", 6.0, "redvelvetcake", "Soft red velvet cake with cream cheese frosting.", "Sweets"),
        Product("Strawberry Cheesecake", 7.0, "strawberrycheesecake", "Creamy cheesecake topped with strawberries.", "Sweets"),
        Product("Tiramisu", 6.0, "tiramisu", "Coffee-flavored Italian dessert.", "Sweets")
    )
}
