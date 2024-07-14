package com.example.proIt.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("my-app")
@AnonymousAllowed
public class NavBarLayout extends AppLayout implements RouterLayout {

    public NavBarLayout() {

        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Weather App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);

    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Location Search", "/location/search",
                        VaadinIcon.SEARCH.create()),
                new SideNavItem("Favorite", "/location/favorite", VaadinIcon.HEART.create()),
                new SideNavItem("Log Out", "/logout",
                        VaadinIcon.OUT.create()));

        return sideNav;
    }
}
