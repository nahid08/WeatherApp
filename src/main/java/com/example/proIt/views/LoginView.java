package com.example.proIt.views;

import com.example.proIt.model.FavoriteLocation;
import com.example.proIt.model.User;
import com.example.proIt.service.FavoriteLocationService;
import com.example.proIt.service.SecurityService;
import com.example.proIt.service.UserService;
import com.helger.commons.string.StringHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    UserService userService;

    FavoriteLocationService favoriteLocationService;

    SecurityService securityService;

    private LoginForm login = new LoginForm();

    public LoginView(UserService userService, FavoriteLocationService favoriteLocationService, SecurityService securityService) {
        this.securityService = securityService;
        this.favoriteLocationService = favoriteLocationService;
        this.userService = userService;

        if(this.securityService.getAuthenticatedUser() != null) {
            UI.getCurrent().navigate(LocationSearchView.class);
        }

        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");
        login.addLoginListener(e -> {
            Optional<User> user = this.userService.getUserByName(e.getUsername());
            if(user.isPresent() == false || !StringHelper.isNotEmpty(e.getUsername()) || !StringHelper.isNotEmpty(e.getPassword())) {
                Notification.show("Invalid User");
            } else {
                VaadinSession.getCurrent().setAttribute("user", user.get());
                List<FavoriteLocation> favoriteLocationList = favoriteLocationService.getFavoriteLocationByUser(user.get());
                VaadinSession.getCurrent().setAttribute("favoriteLocationList", favoriteLocationList != null ? favoriteLocationList : new ArrayList<FavoriteLocation>());
                UI.getCurrent().navigate(LocationSearchView.class);
            }

        });

        addRegisterButton();
        add(new H1("Log In"), login);

    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    private void addRegisterButton() {
        Button registrationButton = new Button("Register As A New User!", e -> {
            UI.getCurrent().navigate(RegistrationView.class);
        });
        registrationButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        add(registrationButton);
    }
}