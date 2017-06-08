package com.key.SSL_Cert;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class keyGen extends UI {
	KeystoreGenForm genForm = new KeystoreGenForm();
	PemGenForm pemGenForm = new PemGenForm();
	DetailsForm detailsForm = new DetailsForm();
	ImportKeyForm importKeyForm = new ImportKeyForm();
	final VerticalLayout layout = new VerticalLayout();
	final HorizontalLayout buttons_layout = new HorizontalLayout();
	final Button keystoreGen_btn = new Button("Generating Keystore", this::addKeysotreGenForm);
	final Button pemGen_btn = new Button("Generating PEM file", this::addPEMGenForm);
	final Button importKey_btn = new Button("Importing SPC file", this::addImportKeyForm);
	final Button details_bnt = new Button("Details", this::addDetailsForm);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	setContent(layout);
    	buttons_layout.setSizeFull();
    	keystoreGen_btn.setSizeFull();
    	pemGen_btn.setSizeFull();
    	importKey_btn.setSizeFull();
    	details_bnt.setSizeFull();
    	buttons_layout.addComponents(keystoreGen_btn, pemGen_btn, importKey_btn, details_bnt);
    	layout.addComponent(buttons_layout);
    	layout.addComponent(genForm);
    	layout.setComponentAlignment(genForm, Alignment.TOP_CENTER);
    	keystoreGen_btn.setEnabled(false);
    }

    public void addKeysotreGenForm(Button.ClickEvent event)
    {
    	keystoreGen_btn.setEnabled(false);
    	pemGen_btn.setEnabled(true);
    	importKey_btn.setEnabled(true);
    	details_bnt.setEnabled(true);
    	layout.removeAllComponents();
    	layout.addComponent(buttons_layout);
    	layout.addComponent(genForm);
    }

    public void addPEMGenForm(Button.ClickEvent event)
    {
    	keystoreGen_btn.setEnabled(true);
    	pemGen_btn.setEnabled(false);
    	importKey_btn.setEnabled(true);
    	details_bnt.setEnabled(true);
    	layout.removeAllComponents();
    	layout.addComponent(buttons_layout);
    	layout.addComponent(pemGenForm);
    }

    public void addImportKeyForm(Button.ClickEvent event)
    {
    	keystoreGen_btn.setEnabled(true);
    	pemGen_btn.setEnabled(true);
    	importKey_btn.setEnabled(false);
    	details_bnt.setEnabled(true);
    	layout.removeAllComponents();
    	layout.addComponent(buttons_layout);
    	layout.addComponent(importKeyForm);
    }

    public void addDetailsForm(Button.ClickEvent event)
    {
    	keystoreGen_btn.setEnabled(true);
    	pemGen_btn.setEnabled(true);
    	importKey_btn.setEnabled(true);
    	details_bnt.setEnabled(false);
    	layout.removeAllComponents();
    	layout.addComponent(buttons_layout);
    	layout.addComponent(detailsForm);
    }

    @WebServlet(urlPatterns = "/*", name = "keyGenServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = keyGen.class, productionMode = false)
    public static class keyGenServlet extends VaadinServlet {
    }
}
