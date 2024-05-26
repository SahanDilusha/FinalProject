

package model;


public class ManagerDashbordModelMenu {

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(String subMenu) {
        this.subMenu = subMenu;
    }
    
    public ManagerDashbordModelMenu(){
        
    }
    
    private String menuName;
    private String subMenu;
    
}
