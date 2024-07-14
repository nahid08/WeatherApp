package com.example.proIt.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView() {
        add(new H1("Welcome to Weather Forecast Application"));
        addLoginButton();
        addRegistrationButton();
        setAlignItems(Alignment.CENTER);
    }

    void addLoginButton() {
        Button loginButton = new Button("Log In", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        add(loginButton);
    }

    public void addRegistrationButton() {
        Button registrationButton = new Button("Register as a new user!", e -> {
            UI.getCurrent().navigate(RegistrationView.class);
        });
        registrationButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        add(registrationButton);
    }

}
