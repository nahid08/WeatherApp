package com.example.proIt.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("logout")
@AnonymousAllowed
public class LogoutView extends VerticalLayout {

    public LogoutView() {
        VaadinSession.getCurrent().getSession().invalidate();
    }
}
