package com.key.SSL_Cert;

import java.io.IOException;

import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class KeystoreGenForm extends FormLayout {
	final TextField storeName_tf = new TextField("Keystore Filename");
    final TextField alias_tf = new TextField("Alias");
    final TextField name_tf = new TextField("First and Last name");
    final TextField org_tf = new TextField("Organization");
    final TextField city_tf = new TextField("City or Locality");
    final TextField state_tf = new TextField("State or Province");
    final TextField code_tf = new TextField("Two-letter Country Code");
    final PasswordField keyPwd_pf = new PasswordField("Key password");
    final PasswordField keyRePwd_pf = new PasswordField("Confirm");
    final PasswordField storePwd_pf = new PasswordField("Keystore password");
    final PasswordField storeRePwd_pf = new PasswordField("Confirm");
    final Button generate_btn = new Button("Generate", this::generateKeyfile);
    
    private static final String ERROR_MATCH = "Passwords must match";
    private static final String ERROR_EMPTY = " must be inputed.";
    
    public KeystoreGenForm()
    {
    	configureComponents();
    	validateComponents();
    	buildLayout();
    }
    
    private void configureComponents()
    {
    	storeName_tf.setValue("codesignstore");
    	
        alias_tf.setInputPrompt("ex. codesigncert");
        alias_tf.setImmediate(true);
        
        name_tf.setInputPrompt("ex. James Taylor");
        name_tf.setImmediate(true);
        
        org_tf.setInputPrompt("ex. End 2 End Technologies");
        org_tf.setImmediate(true);
        
        city_tf.setInputPrompt("ex. SAINT PAUL");
        city_tf.setImmediate(true);
        
        state_tf.setInputPrompt("ex. MN");
        state_tf.setImmediate(true);
        
        code_tf.setInputPrompt("ex. US");
        code_tf.setImmediate(true);
        code_tf.setMaxLength(2);
        
        generate_btn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        generate_btn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        
        setMargin(true);
    }
    
    private void validateComponents()
    {
    	storeName_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("The keystore filename" + ERROR_EMPTY);
				}
			}
		});
    	
    	alias_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("Your alias" + ERROR_EMPTY);
				}
			}
		});
    	
    	name_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("Your first and last name" + ERROR_EMPTY);
				}
			}
		});
    	
    	org_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("Your organization" + ERROR_EMPTY);
				}
			}
		});
    	
    	city_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("Your city or locality" + ERROR_EMPTY);
				}
			}
		});
    	
    	state_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("Your state or province" + ERROR_EMPTY);
				}
			}
		});
    	
    	code_tf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("The country code" + ERROR_EMPTY);
				}
			}
		});
    	
    	keyPwd_pf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("The key password" + ERROR_EMPTY);
				}
			}
		});
        
    	keyRePwd_pf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String password = (String)value;
				if (!password.equals(keyPwd_pf.getValue()))
				{
					throw new InvalidValueException(ERROR_MATCH);
				}
			}
		});
    	
    	storePwd_pf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code.length() == 0)
				{
					throw new InvalidValueException("The keystore password" + ERROR_EMPTY);
				}
			}
		});
        
        storeRePwd_pf.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String password = (String)value;
				if (!password.equals(storePwd_pf.getValue()))
				{
					throw new InvalidValueException(ERROR_MATCH);
				}
			}
		});
    }
    
    private void buildLayout()
    {
    	addComponents(storeName_tf, alias_tf, name_tf, org_tf, city_tf, state_tf, code_tf, keyPwd_pf, keyRePwd_pf, storePwd_pf, storeRePwd_pf, generate_btn);
    }
    
    public void generateKeyfile(Button.ClickEvent event)
    {
    	try {
    		storeName_tf.validate();
    		alias_tf.validate();
    		name_tf.validate();
    		org_tf.validate();
    		city_tf.validate();
    		state_tf.validate();
    		code_tf.validate();
    		keyRePwd_pf.validate();
    		storeRePwd_pf.validate();
    		String execStr = String.format("keytool -genkey -keyalg RSA -keysize 2048 -keystore %s", storeName_tf.getValue());
    		String aliasStr = "-alias " + alias_tf.getValue();
    		String dnameStr = String.format("-dname \"CN=%s,O=%s,C=%s,ST=%s,L=%s\"", name_tf.getValue(), org_tf.getValue(), code_tf.getValue(), state_tf.getValue(), city_tf.getValue());
    		String keypassStr = String.format("-keypass %s", keyPwd_pf.getValue());
    		String storepassStr = String.format("-storepass %s", storePwd_pf.getValue());
    		execStr += " " + aliasStr + " " + dnameStr + " " + keypassStr + " " + storepassStr;
    		Process testProc = Runtime.getRuntime().exec(execStr);
    		Notification.show("Success");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//Notification.show(E);
		}
    }
}
