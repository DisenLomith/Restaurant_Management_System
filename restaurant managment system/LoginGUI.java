import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Cursor;
import java.net.URL;

// DATA MODELS
class User {
     // Encapsulation
   protected String username, password, role;

    public User(String u, String p, String r) {
        this.username = u;
        this.password = p;
        this.role = r;
    }

    public boolean validate(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public String getUserName() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }
}
      // inheritance   ================================ admin interface =============================
class Admin extends User { // admin class and user class inheritance
    public Admin(String u, String p) {
        super(u, p, "Admin");
    }
}
class Staff extends User { public Staff(String u, String p) { super(u, p, "Staff"); } }               // staff class and user class inheritance 
class Customer extends User { public Customer(String u, String p) { super(u, p, "Customer"); } }     // customer class and user class inheritance

     // ================ dashboard and jfram Inheritance =========================
class BaseDashboard extends JFrame {
    protected JButton btnLogout; // login button giving safe mode using Encapsulation

    public BaseDashboard(String title, String name, Color color) {
        setTitle(title);
        setSize(800 , 800); // dashboard size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
      //  setMinimumSize(new Dimension(900, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
header.setBackground(color);

JLabel lbl = new JLabel(title + "  |  User: " + name);
lbl.setForeground(Color.WHITE);
lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));

header.add(lbl);
add(header, BorderLayout.NORTH);

        // Logout Button
        btnLogout = new JButton("Back");

        // Apply red color and styling here
btnLogout.setBackground(new Color(231, 76, 60)); // red background
btnLogout.setForeground(Color.WHITE);            // white text
btnLogout.setFocusPainted(false);               // removes focus outline
btnLogout.setBorder(new RoundedBorder(15)); 
btnLogout.setPreferredSize(new Dimension(150, 40)); 
btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 18));    //  rounded corners

        btnLogout.addActionListener(e -> {
            new LoginGUI();
            dispose();
        });
    }
}
                                    // Inheritance
class AdminDashboard extends BaseDashboard { // ================= admin class and admin dashboard class inheritance =====================
    public AdminDashboard(String name) {
        super("Admin Portal", name, new Color(44, 62, 80)); // dark header

          // SET CUSTOM ICON 
    URL iconURL = getClass().getResource("/images/admin.jpg"); // path to your icon
    if (iconURL != null) {
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
    } else {
        System.out.println("Window icon not found!"); // if it not found image you can see title only
    }

        // Main button panel
        JPanel p = new JPanel(new GridLayout(2, 2, 30, 30));
p.setBorder(new EmptyBorder(60, 80, 60, 80));
p.setOpaque(false); 

JPanel topPanel = new JPanel(new BorderLayout());
topPanel.setBackground(Color.WHITE);
topPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // padding

JLabel welcomeLabel = new JLabel("Welcome, " + name + " (Admin)");
welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
welcomeLabel.setForeground(new Color(39, 174, 96)); // green color
welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

topPanel.add(welcomeLabel, BorderLayout.WEST);

JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
bottomPanel.setBackground(Color.WHITE);
bottomPanel.add(btnLogout);
add(bottomPanel, BorderLayout.SOUTH);

JButton btnManageStaff = createAdminButton("Manage Staff", new Color(52, 152, 219)); // admin dashbord buttons with colors 
JButton btnViewSales  = createAdminButton("Sales Reports", new Color(46, 204, 113)); 
JButton btnEditMenu   = createAdminButton("Edit Menu", new Color(155, 89, 182));
JButton btnInventory  = createAdminButton("Inventory", new Color(241, 196, 15));

        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 16)); // button font size and font style

        p.add(btnManageStaff);
        p.add(btnViewSales);
        p.add(btnEditMenu);
        p.add(btnInventory);

       JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(236, 240, 241),
                0, getHeight(), new Color(189, 195, 199)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
};

backgroundPanel.add(p);
add(backgroundPanel, BorderLayout.CENTER);

        // Manage Staff
        btnManageStaff.addActionListener(e -> openManageStaffDialog());

        // View Sales Reports
        btnViewSales.addActionListener(e -> openSalesReportDialog());

        // Edit Menu Prices
        btnEditMenu.addActionListener(e -> openMenuPriceDialog());

        // Inventory Management
        btnInventory.addActionListener(e -> openInventoryDialog());

        setVisible(true);
    }

    private JButton createAdminButton(String text, Color bgColor) { // Encaspsulation
    JButton btn = new JButton(text);
    btn.setPreferredSize(new Dimension(180, 70)); //  smaller size
    btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
    btn.setForeground(Color.WHITE);
    btn.setBackground(bgColor);
    btn.setFocusPainted(false);
    btn.setBorder(new RoundedBorder(20));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // mouse Hover effect
    btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(bgColor.darker());
        }
        public void mouseExited(MouseEvent e) {
            btn.setBackground(bgColor);
        }
    });

    return btn;
}

    private void openManageStaffDialog() {       // Encaspsulation
        JDialog dialog = new JDialog(this, "Manage Staff", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] columns = {"Username", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Populate staff from LoginGUI.userList
        for (User u : LoginGUI.userList) {  // Polymorphism
            if (u.getRole().equals("Staff")) {
                model.addRow(new Object[]{u.getUserName(), u.getRole()});
            }
        }

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete"); // edit staff details
        btnPanel.add(add);
        btnPanel.add(edit);
        btnPanel.add(delete);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        // Actions
        add.addActionListener(ev -> {
            String username = JOptionPane.showInputDialog(dialog, "Enter username:");
            if (username == null || username.trim().isEmpty()) return;
            String password = JOptionPane.showInputDialog(dialog, "Enter password:");
            if (password == null || password.trim().isEmpty()) return;

            // Check duplicates
            for (User u : LoginGUI.userList) {
                if (u.getUserName().equals(username)) {
                    JOptionPane.showMessageDialog(dialog, "Username already exists!"); // if username already exists
                    return;
                }
            }

            LoginGUI.userList.add(new Staff(username, password));
            model.addRow(new Object[]{username, "Staff"});
        });

        edit.addActionListener(ev -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            User staff = null;
            int count = -1;
            for (User u : LoginGUI.userList) {
                if (u.getRole().equals("Staff")) {
                    count++;
                    if (count == row) staff = u;
                }
            }
            if (staff == null) return;

            String newUsername = JOptionPane.showInputDialog(dialog, "New username:", staff.getUserName()); // check in here for username and password correct 
            if (newUsername == null || newUsername.trim().isEmpty()) return;
            String newPassword = JOptionPane.showInputDialog(dialog, "New password:", staff.password);
            if (newPassword == null || newPassword.trim().isEmpty()) return;

             // use setters for here 
            staff.setUsername(newUsername);
            staff.setPassword(newPassword);

            model.setValueAt(newUsername, row, 0);
        });

        delete.addActionListener(ev -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            User staff = null;
            int count = -1;
            for (User u : LoginGUI.userList) {
                if (u.getRole().equals("Staff")) {
                    count++;
                    if (count == row) staff = u;
                }
            }
            if (staff != null) {
                LoginGUI.userList.remove(staff);
                model.removeRow(row);
            }
        });

        dialog.setVisible(true);
    }

    private void openSalesReportDialog() { // Encaspsulation
        JDialog dialog = new JDialog(this, "Sales Reports", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        String[] columns = {"Date", "Sales (RS)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Mock data
        model.addRow(new Object[]{"2025-12-29", 5000}); // givin a sample sales report data
        model.addRow(new Object[]{"2025-12-28", 7000});
        model.addRow(new Object[]{"2025-12-27", 6200});

        dialog.add(new JScrollPane(table));
        dialog.setVisible(true);
    }

    private void openMenuPriceDialog() {  // price use to safe method Encaspsulation
        JDialog dialog = new JDialog(this, "Edit Menu Prices", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        String[] columns = {"Food Item", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (FoodItem item : CustomerDashboard.menuList) {
            model.addRow(new Object[]{item.getName(), item.getPrice()});
        }

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnUpdate = new JButton("Update Price"); // update price button 
        dialog.add(btnUpdate, BorderLayout.SOUTH);

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            double price = Double.parseDouble(table.getValueAt(row, 1).toString());
            CustomerDashboard.menuList.get(row).setPrice(price);
            JOptionPane.showMessageDialog(dialog, "Price updated!");
        });

        dialog.setVisible(true);
    }

    private void openInventoryDialog() {  // Encaspsulation
        JDialog dialog = new JDialog(this, "Inventory Management", true); // inventory management dialog box
        dialog.setSize(400, 300); 
        dialog.setLocationRelativeTo(this);

        String[] columns = {"Food Item", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); 
        JTable table = new JTable(model);

        for (FoodItem item : CustomerDashboard.menuList) {
            model.addRow(new Object[]{item.getName(), item.getStock()});
        }

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
 
        JButton btnUpdate = new JButton("Update Stock"); // update stock button
        dialog.add(btnUpdate, BorderLayout.SOUTH);

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int stock = Integer.parseInt(table.getValueAt(row, 1).toString());
            CustomerDashboard.menuList.get(row).setStock(stock);
            JOptionPane.showMessageDialog(dialog, "Stock updated!");
        });

        dialog.setVisible(true);
    }
}

class Order {  // =========================================== order class =====================================
     static int onlineCounter = 10001; // for online orders generating unique IDs
    String table;
    ArrayList<FoodItem> items;
    double total;
    String status;
    String orderType;
    String paymentMethod;

    public Order(String table, ArrayList<FoodItem> items) {
        this.table = table;
        this.items = new ArrayList<>(items);
        this.total = 0;
        for (FoodItem f : items) total += f.getTotalPrice();
        this.orderType = table.startsWith("Online") ? "Online" : "Dine-In";  // determine order type
        this.status = "Pending";   // default for ALL orders
    }

    @Override  // Polymorphism with overiding method 
    public String toString() {
        return "Table: " + table + " | Items: " + items.size() + " | Total: RS " + total + " | Status: " + status;
    }
}
               
class StaffDashboard extends BaseDashboard { // ================ staff page and thier dashnoard Inheritance ================================  
     private JPanel rightBottom; //  Encapsulation panel for right bottom side
     private JTable pendingOrdersTable;

    public static ArrayList<Order> pendingOrders = new ArrayList<>(); // pending orders list using arryas list

    public StaffDashboard(String name) { // constructor code
        super("Kitchen & Order System", name, new Color(230, 126, 34)); // orange header

         // SET CUSTOM ICON
    URL iconURL = getClass().getResource("/images/kitchen.jpg");
    if (iconURL != null) {
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
    } else {
        System.out.println("Window icon not found!"); // if icon not found only title will be shown
    }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + name + "!" + " (Staff)");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(39, 174, 96)); // green
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Background panel with gradient
JPanel backgroundPanel = new JPanel(new BorderLayout()) {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // gradient paint
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(236, 240, 241),
                0, getHeight(), new Color(189, 195, 199)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight()); // fill rectangle 
    }
};

// Staff menu panel with padding
JPanel staffMenuPanel = new JPanel();
staffMenuPanel.setLayout(new BoxLayout(staffMenuPanel, BoxLayout.Y_AXIS));
staffMenuPanel.setBackground(Color.WHITE);

// Separate items by category 
ArrayList<FoodItem> menuItems = new ArrayList<>();   // main menu items list this is main arrys we use for a menu items in our sysrem 2 nd array list
ArrayList<FoodItem> dessertItems = new ArrayList<>();   // dessert items list 3 rd arrys list
 
for (FoodItem item : CustomerDashboard.menuList) {
    if (item.getCategory().equalsIgnoreCase("Main")) {
        menuItems.add(item);
    } else if (item.getCategory().equalsIgnoreCase("Dessert")) {
        dessertItems.add(item);
    }
}

// MENU ITEMS PANEL
JPanel menuPanel = new JPanel(new GridLayout(0, 3, 15, 15));
menuPanel.setBackground(Color.WHITE);
for (FoodItem item : menuItems) {
    menuPanel.add(createFoodCard(item));
}

// Menu Items wrapper
JPanel menuWrapper = new JPanel(new BorderLayout());
menuWrapper.setBackground(Color.WHITE); // optional

// Menu Items title
JLabel menuTitle = new JLabel("Menu Items");
menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
menuTitle.setForeground(new Color(52, 152, 219));
menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
menuWrapper.add(menuTitle, BorderLayout.NORTH);

// Add menu grid to wrapper
menuWrapper.add(menuPanel, BorderLayout.CENTER);

// DESSERT ITEMS PANEL
JPanel dessertPanel = new JPanel(new GridLayout(0, 3, 15, 15));
dessertPanel.setBackground(Color.WHITE);
for (FoodItem item : dessertItems) {
    dessertPanel.add(createFoodCard(item));
}

// Dessert wrapper
JPanel dessertWrapper = new JPanel(new BorderLayout());
dessertWrapper.setBackground(Color.WHITE); // optional

// Dessert title
JLabel dessertTitle = new JLabel("Desserts");
dessertTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
dessertTitle.setForeground(new Color(52, 152, 219));
dessertTitle.setHorizontalAlignment(SwingConstants.CENTER);
dessertWrapper.add(dessertTitle, BorderLayout.NORTH);

// Add dessert grid to wrapper
dessertWrapper.add(dessertPanel, BorderLayout.CENTER);

// ADD TO STAFF PANEL
staffMenuPanel.add(menuWrapper);
staffMenuPanel.add(Box.createVerticalStrut(20)); // spacing
staffMenuPanel.add(dessertWrapper);
staffMenuPanel.add(Box.createVerticalGlue());

// Scroll pane
JScrollPane menuScroll = new JScrollPane(staffMenuPanel);
menuScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
menuScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
menuScroll.getVerticalScrollBar().setUnitIncrement(16);

backgroundPanel.add(menuScroll, BorderLayout.CENTER);
add(backgroundPanel, BorderLayout.CENTER);

        // Bottom Action Bar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // LEFT SIDE  Logout button
JPanel leftBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
leftBottom.setBackground(Color.WHITE);

Color brown = new Color(123, 63, 0); // Brown
btnLogout.setBackground(brown);
btnLogout.setForeground(Color.WHITE);
btnLogout.setFocusPainted(false);
btnLogout.setBorder(new RoundedBorder(15));
btnLogout.setPreferredSize(new Dimension(150, 40));
btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 18));
btnLogout.setOpaque(true);
btnLogout.setContentAreaFilled(true);

// nouse Hover effect
btnLogout.addMouseListener(new MouseAdapter() {
    public void mouseEntered(MouseEvent e) {
        btnLogout.setBackground(brown.darker());
    }

    public void mouseExited(MouseEvent e) {
        btnLogout.setBackground(brown);
    }
});

leftBottom.add(btnLogout);

rightBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
rightBottom.setBackground(Color.WHITE);

JButton btnPending = createStaffButton("View Pending Tables", new Color(46, 204, 113));
 JButton btnCurrentOrder = createStaffButton("Current Order", new Color(52, 152, 219));  // should be yellow

rightBottom.add(btnPending);
 rightBottom.add(btnCurrentOrder);

        bottomPanel.add(leftBottom, BorderLayout.WEST);
        bottomPanel.add(rightBottom, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
        btnPending.addActionListener(e -> openPendingTablesDialog());
        setVisible(true);

    btnCurrentOrder.addActionListener(e -> openWalkInOrderDialog());
    }

    private void openWalkInOrderDialog() {
    JDialog dialog = new JDialog(this, "Take Order for Walk-in Customer", true);
    dialog.setSize(800, 500);
    dialog.setLayout(new BorderLayout());
    dialog.setLocationRelativeTo(this);

    String inputTable = JOptionPane.showInputDialog(dialog, "Enter Table Number / Name:"); // get table name or number
final String table;

if (inputTable == null || inputTable.trim().isEmpty()) {
    table = "Table-" + System.currentTimeMillis();
} else {
    table = inputTable;
}

    // Panel to hold menu items
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new GridLayout(0, 3, 15, 15));
    menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Temp list for this order
    ArrayList<FoodItem> orderItems = new ArrayList<>();
    
   for (FoodItem item : CustomerDashboard.menuList) {
    menuPanel.add(createFoodPanel(item, orderItems, dialog));
}                                                                           

    JScrollPane scroll = new JScrollPane(menuPanel);
    dialog.add(scroll, BorderLayout.CENTER);

// deigned the submit order button in the staff dashboard
JButton btnSubmit = new JButton("Submit Order");
btnSubmit.setPreferredSize(new Dimension(200, 45));
btnSubmit.setBackground(new Color(255 , 165 , 0));
btnSubmit.setForeground(Color.WHITE);
btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 16));
btnSubmit.setFocusPainted(false);
btnSubmit.setBorder(new RoundedBorder(20));
btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));

// mouse Hover effect
btnSubmit.addMouseListener(new MouseAdapter() {
    @Override // overriding method
    public void mouseEntered(MouseEvent e) {
        btnSubmit.setBackground(new Color(230 , 126 , 34)); 
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btnSubmit.setBackground(new Color(230, 126, 34));
    }
});
JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
submitPanel.setBackground(Color.WHITE);
submitPanel.add(btnSubmit);

dialog.add(submitPanel, BorderLayout.SOUTH);

  btnSubmit.addActionListener(ev -> {
    if (orderItems.isEmpty()) {
        JOptionPane.showMessageDialog(dialog, "No items selected!");
        return;
    }

    // Ask for payment method
    String[] options = {"Cash", "Card"};
    String paymentMethod = (String) JOptionPane.showInputDialog(
            dialog,
            "Select Payment Method:", // selecting payment method
            "Payment",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
    );

    if (paymentMethod == null) {
        JOptionPane.showMessageDialog(dialog, "Order cancelled. No payment selected.");
        return;
    }

    //  Set the payment method here
    Order order = new Order(table, orderItems);
    order.paymentMethod = paymentMethod;

    pendingOrders.add(order); // Add order to pending list
    JOptionPane.showMessageDialog(dialog, "Order for " + table + " submitted!");
    dialog.dispose();

    // Update the pending orders table
    updatePendingOrdersTable();
});

    dialog.setVisible(true);
}

    // to show item images in staff to give orders for walk in customers
   private JPanel createFoodPanel(
        FoodItem item,
        ArrayList<FoodItem> orderItems,
        JDialog dialog
) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // IMAGE
    URL imgURL = getClass().getResource(item.getImagePath());
    if (imgURL != null) {
        ImageIcon icon = new ImageIcon(imgURL);
        Image scaled = icon.getImage()
                .getScaledInstance(120, 90, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imgLabel);
    }

    panel.add(Box.createVerticalStrut(8));

    // NAME
    JLabel nameLabel = new JLabel(item.getName());
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(nameLabel);

    // PRICE
    JLabel priceLabel = new JLabel("RS " + item.getPrice());
    priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(priceLabel);

    panel.add(Box.createVerticalStrut(8));

    // ADD BUTTON
    JButton addBtn = new JButton("Add");
    addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

    addBtn.addActionListener(e -> {
        boolean found = false;
        for (FoodItem f : orderItems) {
            if (f.getName().equals(item.getName())) {
                f.incrementQty();
                found = true;
                break;
            }
        }

        if (!found) {
            orderItems.add(new FoodItem(
                    item.getName(),
                    item.getPrice(),
                    item.getImagePath(),
                    item.getCategory()
            ));
        }

        JOptionPane.showMessageDialog(dialog,
                item.getName() + " added!");
    });

    panel.add(addBtn);
    return panel;
}

// Polymorphism 
private JButton createStaffButton(String text, Color bgColor) {
    return createStaffButton(text, bgColor, new RoundedBorder(20));
}

private JButton createStaffButton(String text, Color bgColor, RoundedBorder border) {
    JButton btn = new JButton(text);
    btn.setBackground(bgColor);
    btn.setForeground(Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorder(border);
    btn.setPreferredSize(new Dimension(140, 40));
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // mouse Hover effect
    btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) { btn.setBackground(bgColor.darker()); }
        public void mouseExited(MouseEvent e) { btn.setBackground(bgColor); }
    });

    return btn;
}

   private JPanel createFoodCard(FoodItem item) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(15, new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // Image
    URL imgURL = getClass().getResource(item.getImagePath());
    if (imgURL != null) {
        ImageIcon icon = new ImageIcon(imgURL);
        Image scaled = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(imgLabel);
    }

    card.add(Box.createVerticalStrut(10));

    // Name & Price
    JLabel lblName = new JLabel(item.getName(), JLabel.CENTER);
    lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
    lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(lblName);

    JLabel lblPrice = new JLabel("RS " + item.getPrice(), JLabel.CENTER);
    lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(lblPrice);

    return card;
}

  private void openPendingTablesDialog() {
    if (pendingOrders.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No pending orders!");
        return;
    }

    JDialog dialog = new JDialog(this, "Pending Tables", true);
    dialog.setSize(800, 500);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new BorderLayout());

   String[] columns = {"Order Type", "Table / ID", "Items", "Total (RS)", "Payment", "Status"};
    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    pendingOrdersTable = new JTable(model);
JTable table = pendingOrdersTable; // use the class field

    table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JTextArea area = new JTextArea();
        area.setText(value == null ? "" : value.toString());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(table.getFont());
        area.setOpaque(true);

        if (isSelected) {
            area.setBackground(table.getSelectionBackground());
            area.setForeground(table.getSelectionForeground());
        } else {
            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
        }

        return area;
    }
});
    table.setRowHeight(30);

    for (Order o : pendingOrders) {
    model.addRow(new Object[]{
        o.orderType,                                   // Order Type
        o.table,                                       // Table / ID
        formatItemsVertical(o.items),                 // Items
        o.total,                                      // Total (RS)
        o.paymentMethod != null ? o.paymentMethod : "-", // Payment
        o.status                                      // Status
    });
}
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    JPanel btnPanel = new JPanel();
    JButton btnServe = new JButton("Mark as Served");
    JButton btnDeliver = new JButton("Mark as Delivered");
    JButton btnRemove = new JButton("Remove Order");

    btnPanel.add(btnServe);
    btnPanel.add(btnDeliver);
    btnPanel.add(btnRemove);
    dialog.add(btnPanel, BorderLayout.SOUTH);

btnServe.addActionListener(e -> {  // Polymorphism
    int row = pendingOrdersTable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(dialog, "Select an order first");
        return;
    }

    Order order = pendingOrders.get(row);

    if (order.orderType.equals("Online")) {
        JOptionPane.showMessageDialog(dialog,
                "Online orders must be marked as Delivered");
        return;
    }

    order.status = "Served";
    pendingOrdersTable.getModel().setValueAt("Served", row, 5); // update table
});

    btnRemove.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row != -1) {
            pendingOrders.remove(row);
            model.removeRow(row);
        }
    });

    btnDeliver.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(dialog, "Select an order first");
        return;
    }

    Order order = pendingOrders.get(row);

    if (!order.orderType.equals("Online")) {
        JOptionPane.showMessageDialog(dialog,
                "Only online orders can be delivered");
        return;
    }

    order.status = "Delivered";
    model.setValueAt("Delivered", row, 5);
});

    dialog.setVisible(true);
}

private void updatePendingOrdersTable() {
    if (pendingOrdersTable == null) return;

    DefaultTableModel model = (DefaultTableModel) pendingOrdersTable.getModel();
    model.setRowCount(0);

    for (Order order : pendingOrders) {
        model.addRow(new Object[]{
            order.orderType,
            order.table,
            formatItemsVertical(order.items),
            order.total,
            order.paymentMethod != null ? order.paymentMethod : "-",
            order.status
        });
    }
}
     //  Converts item list to vertical text for JTable
private String formatItemsVertical(ArrayList<FoodItem> items) {
    StringBuilder sb = new StringBuilder();
    for (FoodItem f : items) {
        sb.append(f.toString()).append("\n");
    }
    return sb.toString().trim();
}

}
// Encapsulation
class FoodItem {  // ================================== food irem class ========================================
    private String name;
    private double price;
    private int quantity;
    private int stock;
    private String imagePath;
    private String category;

    public FoodItem(String name, double price, String imagePath , String category) { // constructor
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = 1;
        this.stock = 10;
        this.imagePath = imagePath;
    }

public FoodItem(String name, double price, String imagePath) {
    this(name, price, imagePath, "Main");
}
                  // Encapsulation
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImagePath() { return imagePath; } //  use getter

    public int getQuantity() { return quantity; }
    public void incrementQty() { quantity++; }
    public void decrementQty() { if (quantity > 1) quantity--; }
    public double getTotalPrice() { return price * quantity; }

     // polymorphism
    @Override
    public String toString() {
        return name + " x " + quantity + " = RS " + getTotalPrice();
    }
    public String getCategory() {
        return category;
    }
}
// customer interface
class CustomerDashboard extends BaseDashboard {  // ======================= customer class with their dashbord Inheritance =========================
    public static ArrayList<FoodItem> menuList = new ArrayList<>(); // this is main menu  4 th array list 

static { // all images are stored in the images folder
    if (menuList.isEmpty()) {
        menuList.add(new FoodItem("Burger", 800.0,"/images/bg.jpg", "Main"));
        menuList.add(new FoodItem("Pizza", 1200.0, "/images/ps.jpg", "Main"));
        menuList.add(new FoodItem("Pasta", 1000.0, "/images/pa.jpg", "Main"));
        menuList.add(new FoodItem("Salad", 600.0, "/images/sa.jpg", "Main"));
        menuList.add(new FoodItem("Noodles", 900.0, "/images/no.jpg", "Main"));
        menuList.add(new FoodItem("Fried Rice", 1200.0, "/images/fr.jpg", "Main"));
        menuList.add(new FoodItem("Spring Rolls", 500.0, "/images/sp.jpg", "Main"));
        menuList.add(new FoodItem("Chicken Wings", 700.0, "/images/ch.jpg", "Main"));
        menuList.add(new FoodItem("Pad Thai", 1400.0, "/images/pad.jpg", "Main"));
        // desserts
        menuList.add(new FoodItem("Pudin", 800.0, "/images/pujpg.jpg", "Dessert"));
        menuList.add(new FoodItem("Watalapan", 1000.0, "/images/wa.jpg", "Dessert"));
        menuList.add(new FoodItem("Ice cream", 900.0, "/images/ice.jpg", "Dessert"));
        menuList.add(new FoodItem("Chocolate Lava Cake", 1100.0, "/images/Chocolate Lava Cake.jpg", "Dessert"));
        menuList.add(new FoodItem("New York Cheesecake", 1200.0, "/images/New York Cheesecake.jpg", "Dessert"));
        menuList.add(new FoodItem("Tiramisu", 1000.0, "/images/Tiramisu.jpg", "Dessert"));
        menuList.add(new FoodItem("Churros", 700.0, "/images/Churrosjpg.jpg", "Dessert"));
        menuList.add(new FoodItem("Warm Brownie Sundae", 900.0, "/images/Warm Brownie Sundae.jpg", "Dessert"));
        menuList.add(new FoodItem("Profiteroles", 900.0, "/images/Profiteroles.jpg", "Dessert"));
    }
}

private JButton btnViewCart;
private JTextField txtSearch;
private JPanel menuGrid;
private JButton btnCheckout;
private JLabel lblTotalBill;
private JPanel dessertGrid; 

    private JPanel contentPanel;
    private ArrayList<FoodItem> cart = new ArrayList<>(); // 4 th arrys list for cart items 

    public CustomerDashboard(String name) {
        super("Customer Menu",name , new Color(229,57,53));

        URL iconURL = getClass().getResource("/images/menuss.jpg"); // adjust path
    if (iconURL != null) {
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
    } else {
        System.out.println("Icon not found!");
    }

//  MAIN MENU PANEL
JPanel mainMenuPanel = new JPanel();
mainMenuPanel.add(sectionTitle("Main Dishes"));

mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
mainMenuPanel.setBackground(Color.WHITE);


//  CREATE GRID FOR FOOD CARDS
menuGrid = new JPanel(new GridLayout(0, 5 , 8 , 8));
menuGrid.setBackground(Color.WHITE);

   // Populate menu grid with food cards
  for (FoodItem item : menuList) {
    if (item.getCategory().equals("Main")) {
        menuGrid.add(createFoodCard(
    item.getName(),
    item.getImagePath(),
    item.getPrice()
));

    }
}
// add grid to main panel
mainMenuPanel.add(menuGrid); // add menu grid to main panel

//  DESSERTS SECTION 
mainMenuPanel.add(sectionTitle("Desserts"));

// Create a grid for desserts
dessertGrid = new JPanel(new GridLayout(0, 5 , 8 , 8));
dessertGrid.setBackground(Color.WHITE);

// Populate dessertGrid
for (FoodItem item : menuList) {
    if (item.getCategory().equals("Dessert")) {
        dessertGrid.add(createFoodCard(
            item.getName(),
            item.getImagePath(),
            item.getPrice()
        ));
    }
}
// Add dessert grid to main panel
mainMenuPanel.add(dessertGrid);

         // initialize txtSearch 
    txtSearch = new JTextField();
    txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    txtSearch.setBorder(new RoundedBorder(16));

    // Add listener **inside constructor**
    txtSearch.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent e) {
            filterMenu(txtSearch.getText());
        }
    });

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        btnViewCart = new JButton(" Cart (0)");
        btnViewCart.setBackground(new Color(241, 196, 15)); // YELLOW
        btnViewCart.setForeground(Color.BLACK); // better contrast
        btnViewCart.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnViewCart.setBorder(new RoundedBorder(15));
        btnViewCart.addActionListener(e -> showCartDialog());
        btnViewCart.addMouseListener(new MouseAdapter() {
    public void mouseEntered(MouseEvent e) {
        btnViewCart.setBackground(new Color(243, 156, 18));
    }
    public void mouseExited(MouseEvent e) {
        btnViewCart.setBackground(new Color(241, 196, 15));
    }
});

// CHECKOUT BUTTON 
btnCheckout = new JButton("Checkout");
btnCheckout.setBackground(new Color(155, 89, 182));
btnCheckout.setForeground(Color.WHITE);
btnCheckout.setFont(new Font("Segoe UI", Font.BOLD, 18));
btnCheckout.setBorder(new RoundedBorder(15));
btnCheckout.addActionListener(e -> checkout());

// BOTTOM PANEL
JPanel bottomPanel = new JPanel(new BorderLayout());
bottomPanel.setBackground(Color.WHITE);

JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
leftPanel.setBackground(Color.WHITE);
leftPanel.add(btnCheckout);
leftPanel.add(btnViewCart);

// RIGHT PANEL (Logout)
JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
rightPanel.setBackground(Color.WHITE);
rightPanel.add(btnLogout);

// Make Logout button brown
Color brown = new Color(123, 63, 0);

btnLogout.setBackground(brown);
btnLogout.setForeground(Color.WHITE);
btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 18));
btnLogout.setBorder(new RoundedBorder(15));
btnLogout.setFocusPainted(false);
btnLogout.setOpaque(true);
btnLogout.setContentAreaFilled(true);
btnLogout.setPreferredSize(new Dimension(150, 40));

// Optional hover effect
btnLogout.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseEntered(MouseEvent e) {
        btnLogout.setBackground(brown.darker());
    }
    @Override
    public void mouseExited(MouseEvent e) {
        btnLogout.setBackground(brown);
    }
});

// ADD PANELS
bottomPanel.add(leftPanel, BorderLayout.WEST);
bottomPanel.add(rightPanel, BorderLayout.EAST);

// ADD TO FRAME
add(bottomPanel, BorderLayout.SOUTH);

// Header
JPanel topPanel = new JPanel(new BorderLayout());
topPanel.setBackground(Color.WHITE);

     JLabel header = new JLabel("Welcome, " + name + "!");
        header.setFont(new Font("Segoe UI", Font.BOLD, 50));
        header.setForeground(new Color(178, 190, 181));
        header.setHorizontalAlignment(SwingConstants.LEFT); // RIGHT ALIGN
        header.setBorder(new EmptyBorder(20, 20, 20, 20)); //  padding

        topPanel.add(header, BorderLayout.WEST);

// 🔍 Search Bar (FIXED WIDTH)
JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT , 30 , 20));
searchPanel.setBorder(new EmptyBorder(0, 20, 30, 20));
searchPanel.setBackground(Color.WHITE);

// Create label and set font
JLabel searchLabel = new JLabel("Search Food Items:");
searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 19)); // Increase font size
searchPanel.add(searchLabel);

// Add text field
txtSearch.setPreferredSize(new Dimension(300, 40));
searchPanel.add(txtSearch);

topPanel.add(searchPanel, BorderLayout.EAST);

contentPanel.add(topPanel, BorderLayout.NORTH);

JScrollPane scrollPane = new JScrollPane(mainMenuPanel);
scrollPane.getVerticalScrollBar().setUnitIncrement(16);
contentPanel.add(scrollPane, BorderLayout.CENTER);

scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // vertical scroll bar
scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // no horizontal scroll bar
scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling

    add(contentPanel);
    setVisible(true);
}

private void filterMenu(String query) {
    query = query.toLowerCase();

    // Clear both grids
    menuGrid.removeAll();
    dessertGrid.removeAll(); 

    // Separate items by category
    ArrayList<FoodItem> mainMatches = new ArrayList<>(); // main dish matches arrays using 6 th arrys list
    ArrayList<FoodItem> mainOthers = new ArrayList<>();
    ArrayList<FoodItem> dessertMatches = new ArrayList<>();
    ArrayList<FoodItem> dessertOthers = new ArrayList<>();

    for (FoodItem item : menuList) {
        boolean matches = item.getName().toLowerCase().contains(query);

        if (item.getCategory().equals("Main")) {
            if (matches) mainMatches.add(item);
            else mainOthers.add(item);
        } else if (item.getCategory().equals("Dessert")) {
            if (matches) dessertMatches.add(item);
            else dessertOthers.add(item);
        }
    }

    // Add main dishes (matches first)
    for (FoodItem item : mainMatches) menuGrid.add(createFoodCard(item.getName(), item.getImagePath(), item.getPrice()));
    for (FoodItem item : mainOthers) menuGrid.add(createFoodCard(item.getName(), item.getImagePath(), item.getPrice()));

    // Add desserts (matches first)
    for (FoodItem item : dessertMatches) dessertGrid.add(createFoodCard(item.getName(), item.getImagePath(), item.getPrice()));
    for (FoodItem item : dessertOthers) dessertGrid.add(createFoodCard(item.getName(), item.getImagePath(), item.getPrice()));

    // Refresh UI
    menuGrid.revalidate();
    menuGrid.repaint();
    dessertGrid.revalidate();
    dessertGrid.repaint();
}

private void showCartDialog() {
    if (cart.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Your cart is empty!",
                "Cart",
                JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    // message dialog box 
    JDialog cartDialog = new JDialog(this, "Your Cart", true);
    cartDialog.setSize(490, 500);
    cartDialog.setLocationRelativeTo(this);
    cartDialog.setLayout(new BorderLayout());

    // Panel to hold cart items
    JPanel itemsPanel = new JPanel();
    itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
    itemsPanel.setBackground(Color.WHITE);

    // Add each item as a row
    for (FoodItem item : cart) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        row.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(item.getName());
        lblName.setPreferredSize(new Dimension(120, 25));

        JButton btnMinus = new JButton("-");
        JButton btnPlus = new JButton("+");
        JButton btnRemove = new JButton("Remove");

        JLabel lblQty = new JLabel(String.valueOf(item.getQuantity()));
        lblQty.setPreferredSize(new Dimension(30, 25));

        JLabel lblTotal = new JLabel("RS " + item.getTotalPrice());
        lblTotal.setPreferredSize(new Dimension(60, 25));

        // Button actions
        btnPlus.addActionListener(e -> {
            item.incrementQty();
            lblQty.setText(String.valueOf(item.getQuantity()));
            lblTotal.setText("RS " + item.getTotalPrice());
            updateCartCount();
            lblTotalBill.setText("Total: RS " + calculateCartTotal());
        });

    btnMinus.addActionListener(e -> {
    item.decrementQty();
    lblQty.setText(String.valueOf(item.getQuantity()));
    lblTotal.setText("RS " + item.getTotalPrice());
    updateCartCount();
    lblTotalBill.setText("Total: RS " + calculateCartTotal());
});

        btnRemove.addActionListener(e -> {
            cart.remove(item);
            itemsPanel.remove(row);
            itemsPanel.revalidate();
            itemsPanel.repaint();
            updateCartCount();

           lblTotalBill.setText("Total: RS " + calculateCartTotal());
        });

        row.add(lblName); // add item name to the row
        row.add(btnMinus);
        row.add(lblQty);
        row.add(btnPlus);
        row.add(lblTotal);
        row.add(btnRemove);

        itemsPanel.add(row);
    }

    // Wrap in scroll pane
    JScrollPane scrollPane = new JScrollPane(itemsPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    cartDialog.add(scrollPane, BorderLayout.CENTER);

    // Total and Checkout button
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottomPanel.setBackground(Color.WHITE);

    // NEW (field)
lblTotalBill = new JLabel("Total: RS " + calculateCartTotal());

    JButton btnCheckoutDialog = new JButton("Checkout");
    btnCheckoutDialog.addActionListener(e -> {
        cartDialog.dispose();
        checkout();
    });

    bottomPanel.add(lblTotalBill);
    bottomPanel.add(btnCheckoutDialog);

    cartDialog.add(bottomPanel, BorderLayout.SOUTH);

    cartDialog.setVisible(true);
}

    // abstraction 
    private void checkout() {
    if (cart.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Your cart is empty!",
                "Checkout",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Payment options
    String[] options = {"Card"};  // only card payment for online orders 
    String paymentMethod = (String) JOptionPane.showInputDialog(
            this,
            "Select payment method:", // message box for payment method
            "Payment",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
    );

    if (paymentMethod == null) return;

    // Order confirmation
    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Total Bill: RS " + calculateCartTotal() +
            "\nPayment: " + paymentMethod +
            "\n\nConfirm Order?",
            "Confirm Order",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {

    // Convert cart orderItems
    ArrayList<FoodItem> orderItems = new ArrayList<>();
    for (FoodItem item : cart) {
        FoodItem copy = new FoodItem(
                item.getName(),
                item.getPrice(),
                item.getImagePath(),
                item.getCategory()
        );

        for (int i = 1; i < item.getQuantity(); i++) {
            copy.incrementQty();
        }

        orderItems.add(copy);
    }

   Order onlineOrder = new Order(
    "Online-" + String.format("%03d", Order.onlineCounter++),
    orderItems
);

    onlineOrder.paymentMethod = paymentMethod; // Cash or Card
    onlineOrder.status = "Pending";          // Online orders delivered
    onlineOrder.orderType = "Online";          // safety

    StaffDashboard.pendingOrders.add(onlineOrder);

     //  Show delivery confirmation popup
  JOptionPane.showMessageDialog(
    this,
    "Your order has been confirmed successfully!\nEstimated preparation time: 15 minutes\n See you next time!",
    "Thank You",
    JOptionPane.INFORMATION_MESSAGE // information message type message box 
);

// Close CustomerDashboard and return to LoginGUI
this.dispose();

//  Open Login window
new LoginGUI();
}
}
    // abstraction
    private double calculateCartTotal() {
        double total = 0;
        for (FoodItem item : cart) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private void updateCartCount() {
    int totalItems = 0;
    for (FoodItem item : cart) {
        totalItems += item.getQuantity();
    }
    btnViewCart.setText(" Cart (" + totalItems + ")");
}

      private JLabel sectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setBorder(new EmptyBorder(20, 20, 10, 20));
        lbl.setForeground(new Color(52, 73, 94));
        return lbl;
    }

    private JPanel createFoodCard(String name, String imagePath, double price) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(15, new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // Image
    URL imgURL = getClass().getResource(imagePath);
if (imgURL == null) {
    System.out.println("Image not found: " + imagePath);
    return new JPanel();
}

ImageIcon icon = new ImageIcon(imgURL);
Image scaled = icon.getImage()
        .getScaledInstance(150, 120, Image.SCALE_SMOOTH);
JLabel imgLabel = new JLabel(new ImageIcon(scaled));

    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(imgLabel);
    card.add(Box.createVerticalStrut(10));

    // Name & Price
    JLabel lblName = new JLabel(name, JLabel.CENTER);
    lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
    lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(lblName);

    JLabel lblPrice = new JLabel("RS " + price, JLabel.CENTER);
    lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(lblPrice);

    card.putClientProperty("nameLabel", lblName);
    card.add(Box.createVerticalStrut(10));

    // Add to Cart button
    JButton btnAdd = new JButton("Add to Cart");
    Color redColor = new Color(231, 76, 60); // RED
    btnAdd.setForeground(Color.WHITE);
    btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
    btnAdd.setBorder(new RoundedBorder(10));
    btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
    card.add(btnAdd);

   btnAdd.setBackground(redColor);

    btnAdd.addMouseListener(new MouseAdapter() {
    public void mouseEntered(MouseEvent e) {
        btnAdd.setBackground(redColor.darker()); //  hover effect
    }
    public void mouseExited(MouseEvent e) {
        btnAdd.setBackground(redColor);
    }
});

   btnAdd.addActionListener(e -> {
    btnAdd.setEnabled(false);
    boolean found = false;
    for (FoodItem item : cart) {
        if (item.getName().equals(name)) { item.incrementQty(); found = true; break; }
    }
    if (found) {
}
    if (!found) {
        for (FoodItem item : CustomerDashboard.menuList) {
            if (item.getName().equals(name)) {
                cart.add(new FoodItem(name, price, item.getImagePath()));
                break;
            }
        }
    }

    updateCartCount();

    if (lblTotalBill != null) {
        lblTotalBill.setText("Total: RS " + calculateCartTotal()); // update total bill if cart dialog is open
    }

    javax.swing.Timer timer = new javax.swing.Timer(200, ev -> btnAdd.setEnabled(true));
    timer.setRepeats(false);
    timer.start();
});

    // Card hover effect
    card.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) { card.setBackground(new Color(245, 245, 245)); }
        public void mouseExited(MouseEvent e) { card.setBackground(Color.WHITE); }
    });

    return card;
}
}

class RoundedBorder extends AbstractBorder { //  ================================ rounder border and abstracboarder classes =======================
    private int radius;
    private Color borderColor;

    public RoundedBorder(int radius)
    {
        this.radius = radius;
        this.borderColor = Color.GRAY; // DEFAULT
    }
    public RoundedBorder(int radius, Color color) 
    {
        this.radius = radius;
        this.borderColor = color;
    }

    @Override  // Polymorphism (Method Overriding)
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(8, 12, 8, 12);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = 12;
        insets.top = insets.bottom = 8;
        return insets;
    }
}

//   ======================================== MAIN LOGIN INTERFACE =========================================
public class LoginGUI extends JFrame implements ActionListener { //===================== login and jfram classes Inheritance====================
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin, btnCustomerOrder;
    JLabel lblMsg;
    JPanel loginContainerPanel;

    // UI colors
    Color primaryColor = new Color(44, 62, 80);
    Color accentColor = new Color(231 , 76 , 60);
    Color textColor = Color.WHITE;

    static ArrayList<User> userList = new ArrayList<>(); // array list for users 

    public LoginGUI() {
        if (userList.isEmpty()) {
            userList.add(new Admin("Kaveen", "kaveen123@"));   // access for a admin and staff
            userList.add(new Admin("Lomith", "lomith123@"));
            userList.add(new Staff("Disen", "disen123@"));    // staff access
            userList.add(new Staff("Dinusha", "dinusha123@"));
            userList.add(new Staff("Niloosha", "niloosha123@"));
        }

        setTitle("Restaurant Management");
        setSize(700, 830);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setVisible(true);

        // SET ICON HERE
URL iconURL = getClass().getResource("/images/icons.jpg");

if (iconURL != null) {
    ImageIcon icon = new ImageIcon(iconURL);

    Image scaledImage = icon.getImage().getScaledInstance(
            70,     // width
            70,     // height
            Image.SCALE_SMOOTH
    );

    setIconImage(scaledImage);
} else {
    System.out.println("App icon not found!");
}

setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
setResizable(true);
setVisible(true);

// main container with padding
JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
mainPanel.setBackground(Color.WHITE);
mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

//  HEADER SECTION 
JPanel headerPanel = new JPanel();
headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
headerPanel.setBackground(Color.WHITE);

URL imgURL = getClass().getResource("/images/ii.jpg");

if (imgURL != null) {
    ImageIcon originalIcon = new ImageIcon(imgURL);

    int baseWidth = 120;
    double scaleFactor = 2.3;

    int newWidth = (int) (baseWidth * scaleFactor);
    int newHeight = (newWidth * originalIcon.getIconHeight()) / originalIcon.getIconWidth();

    Image scaledImage = originalIcon.getImage()
            .getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    headerPanel.add(Box.createVerticalStrut(20));
    headerPanel.add(imageLabel);
    headerPanel.add(Box.createVerticalStrut(20));
} else {
    System.out.println("Login image not found!");
}

// Title Label
JLabel lblTitle = new JLabel("Hello again!");  // title label
lblTitle.setFont(new Font("Serif", Font.BOLD, 40));
lblTitle.setForeground(new Color(48, 35, 120));
lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

// Add to Header Panel
headerPanel.add(Box.createVerticalStrut(10));
headerPanel.add(lblTitle);

//  HAMBURGER BUTTON 
JButton btnMenu = new JButton("\u2630"); // Unicode for  (three lines)
btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 24));
btnMenu.setForeground(Color.WHITE);
btnMenu.setBackground(primaryColor);
btnMenu.setFocusPainted(false);
btnMenu.setBorderPainted(false);
btnMenu.setOpaque(true);
btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Align it to top-right
JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
topRightPanel.setBackground(Color.WHITE);
topRightPanel.add(btnMenu);

// Add topRightPanel to headerPanel (at the top)
headerPanel.add(topRightPanel, 0); // Add at index 0 to place it above other header components

// Add Header to Main Panel
mainPanel.add(headerPanel, BorderLayout.NORTH);

//TOGGLE VISIBILITY 
btnMenu.addActionListener(ae -> {
    loginContainerPanel.setVisible(!loginContainerPanel.isVisible());
    mainPanel.revalidate();
    mainPanel.repaint();
}); 

JPanel inputPanel = new JPanel(new GridBagLayout());
inputPanel.setBackground(Color.WHITE);
inputPanel.setBorder(new EmptyBorder(20, 25, 10, 10));

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(8, 8, 8, 8);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.anchor = GridBagConstraints.CENTER;

JLabel lblEmail = new JLabel("Username");
lblEmail.setForeground(new Color(64, 63, 163));
lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));

gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 1;
gbc.weightx = 0.6;  // label should not stretch
inputPanel.add(lblEmail, gbc);

// Username placeholder
txtUser = new JTextField("Enter your username");
txtUser.setForeground(Color.GRAY);
txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
txtUser.setBorder(new RoundedBorder(12, Color.GRAY));
txtUser.setPreferredSize(new Dimension(450, 40));

txtUser.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusGained(java.awt.event.FocusEvent e) {
        if (txtUser.getText().equals("Enter your username")) {  // placeholder text
            txtUser.setText("");
            txtUser.setForeground(Color.BLACK); // text color for user input
        }
    }
    @Override
    public void focusLost(java.awt.event.FocusEvent e) {
        if (txtUser.getText().isEmpty()) {
            txtUser.setForeground(Color.GRAY);
            txtUser.setText("Enter your username");
        }
    }
});

gbc.gridx = 0;
gbc.gridy = 1;
gbc.gridwidth = 2;   // span both columns
gbc.weightx = 1.0;   // allow stretching horizontally
gbc.fill = GridBagConstraints.HORIZONTAL;
inputPanel.add(txtUser, gbc);

JLabel lblPassword = new JLabel("Password");
lblPassword.setForeground(new Color(64, 63, 163));
lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));

gbc.gridx = 0;
gbc.gridy = 2;
gbc.gridwidth = 2;
gbc.weightx = 1.0;  // label does not stretch
inputPanel.add(lblPassword, gbc);

// Password placeholder
txtPass = new JPasswordField("Enter your password"); // placeholder text
txtPass.setForeground(Color.GRAY);
txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
txtPass.setBorder(new RoundedBorder(12, Color.GRAY));
txtPass.setPreferredSize(new Dimension(450, 40));
txtPass.setEchoChar((char) 0); // show placeholder text

txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusGained(java.awt.event.FocusEvent e) {
        String passText = new String(txtPass.getPassword());
        if (passText.equals("Enter your password")) {
            txtPass.setText("");
            txtPass.setForeground(Color.BLACK);
            txtPass.setEchoChar('•'); // hide user input
        }
    }
    @Override
    public void focusLost(java.awt.event.FocusEvent e) {
        String passText = new String(txtPass.getPassword());
        if (passText.isEmpty()) {
            txtPass.setForeground(Color.GRAY);
            txtPass.setText("Enter your password");
            txtPass.setEchoChar((char) 0); // show placeholder again
        }
    }
});

gbc.gridx = 0;
gbc.gridy = 3;
gbc.gridwidth = 2;      
gbc.weightx = 1.0;     // allow stretching
gbc.fill = GridBagConstraints.HORIZONTAL;
inputPanel.add(txtPass, gbc);

btnLogin = new JButton("LOGIN");
btnLogin.setBackground(new Color(65, 105, 225));
btnLogin.setForeground(Color.WHITE);
btnLogin.setFocusPainted(false);
btnLogin.setBorderPainted(false);
btnLogin.setOpaque(true);
btnLogin.setBorder(new RoundedBorder(10));
btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
btnLogin.setPreferredSize(new Dimension(160, 45));

gbc.gridy = 4;
gbc.fill = GridBagConstraints.NONE; // prevent stretching
inputPanel.add(btnLogin, gbc);

lblMsg = new JLabel("Login to continue", JLabel.CENTER);
lblMsg.setForeground(new Color(0, 102, 204));

gbc.gridy = 5;
gbc.fill = GridBagConstraints.HORIZONTAL;
inputPanel.add(lblMsg, gbc);

loginContainerPanel = new JPanel(new GridBagLayout());
loginContainerPanel.setBackground(Color.WHITE);
loginContainerPanel.setVisible(false);

GridBagConstraints c = new GridBagConstraints();
c.gridx = 0;
c.gridy = 0;
c.weightx = 1.0;   // stretch horizontally
c.weighty = 0;     // no vertical stretching
c.fill = GridBagConstraints.HORIZONTAL; // < change from NONE to HORIZONTAL
c.anchor = GridBagConstraints.CENTER;

loginContainerPanel.add(inputPanel, c);

// Add to main panel
mainPanel.add(loginContainerPanel, BorderLayout.CENTER);

btnLogin.addMouseListener(new MouseAdapter() {
    public void mouseEntered(MouseEvent e) {
        btnLogin.setBackground(new Color(100, 149, 237)); // Lighter blue
    }
    public void mouseExited(MouseEvent e) {
        btnLogin.setBackground(new Color(65, 105, 225)); // Original blue
    }
});

btnCustomerOrder = new JButton("CUSTOMER ORDER");
btnCustomerOrder.setBackground(new Color(255, 140, 0)); // Dark Orange
btnCustomerOrder.setForeground(Color.WHITE);
btnCustomerOrder.setFocusPainted(false);
btnCustomerOrder.setBorderPainted(false);
btnCustomerOrder.setOpaque(true);
btnCustomerOrder.setBorder(new RoundedBorder(15));
btnCustomerOrder.setFont(new Font("Segoe UI", Font.BOLD, 18));
btnCustomerOrder.setPreferredSize(new Dimension(200, 45)); // wider
btnCustomerOrder.setCursor(new Cursor(Cursor.HAND_CURSOR));
btnCustomerOrder.setMargin(new Insets(10, 25, 10, 25));

Timer pulseTimer = new Timer(500, e -> {
    Color bg = btnCustomerOrder.getBackground();
    btnCustomerOrder.setBackground(bg.equals(new Color(255, 165, 0)) ? new Color(255, 140, 0) : new Color(255, 165, 0));
});
pulseTimer.start();

btnCustomerOrder.addMouseListener(new MouseAdapter() {
    @Override // Polymorphism
    public void mouseEntered(MouseEvent e) {
        btnCustomerOrder.setBackground(new Color(255, 165, 60)); // lighter orange
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btnCustomerOrder.setBackground(new Color(255, 140, 0)); // original orange
    }
});

mainPanel.add(loginContainerPanel, BorderLayout.CENTER);

mainPanel.add(btnCustomerOrder, BorderLayout.SOUTH); // always visible

btnLogin.addActionListener(this);
btnCustomerOrder.addActionListener(this);
this.getRootPane().setDefaultButton(btnLogin);
        add(mainPanel);
        setVisible(true);
}

     @Override  // Polymorphism
public void actionPerformed(ActionEvent e) {
    String uName = txtUser.getText().trim();
    String uPass = new String(txtPass.getPassword()).trim();

    // CUSTOMER ORDER (no login required)
if (e.getSource() == btnCustomerOrder) {

    String name = JOptionPane.showInputDialog(
        this,
        "Enter your name:",
        "Customer Name",
        JOptionPane.PLAIN_MESSAGE
    );

    if (name == null || name.trim().isEmpty()) {
        name = "Guest";
    }

    new CustomerDashboard(name);
    dispose();
    return;
}

    //  login button action
    if (e.getSource() == btnLogin) {

        if (uName.isEmpty() || uName.equals("Enter your username") || // validation for username and password error messages
            uPass.isEmpty() || uPass.equals("Enter your password")) {
            lblMsg.setText("Please enter valid credentials!");
            lblMsg.setForeground(Color.RED);
            return;
        }
        //main login validation
        for (User u : userList) { // Polymorphism see which user role on this login
            if (u.validate(uName, uPass)) {
                if (u.getRole().equals("Admin")) // admin dashboard
                    new AdminDashboard(u.getUserName()); 
                else if (u.getRole().equals("Staff")) // staff dashboard
                    new StaffDashboard(u.getUserName());
                else
                    new CustomerDashboard(u.getUserName());

                dispose();
                return;
            }
        }

        lblMsg.setText("Invalid Login!");
        lblMsg.setForeground(Color.RED); // invalid login message
    }
}
    public static void main(String[] args) {
        new LoginGUI(); // launch login interface 
    }
}