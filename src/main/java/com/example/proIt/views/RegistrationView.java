package com.example.proIt.views;

import com.example.proIt.model.User;

import com.example.proIt.service.SecurityService;
import com.example.proIt.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route("registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {


    UserService userService;
    SecurityService securityService;


    public  RegistrationView(UserService userService, SecurityService securityService) {

        this.userService = userService;
        this.securityService = securityService;

        if(this.securityService.getAuthenticatedUser() != null) {
            UI.getCurrent().navigate(FavoriteLocationListView.class);
        }

        add(new H1("User Registration Form"));

        configureForm();
        addLoginButton();
        setAlignItems(Alignment.CENTER);
    }

    public void configureForm()  {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button submitButton = new Button("Submit", e -> {
            if(validateUser(usernameField.getValue())) {
                return;
            }
            User user = new User();
            user.setUserName(usernameField.getValue());
            user.setPassword(passwordField.getValue());
            userService.save(user);
            UI.getCurrent().navigate(LoginView.class);
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField, submitButton);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        Div formDiv = new Div(formLayout);
        formDiv.setClassName(Style.AlignItems.CENTER.name());

        add(formDiv);
    }

    void addLoginButton() {
        Button loginButton = new Button("Log In", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        add(loginButton);
    }

    private boolean validateUser(String name) {
        boolean isUserExist = userService.isUserExist(name);
        if(isUserExist) {
            Notification.show("User is already registered", 3000, Notification.Position.MIDDLE);

        }
        return isUserExist;
    }
}
