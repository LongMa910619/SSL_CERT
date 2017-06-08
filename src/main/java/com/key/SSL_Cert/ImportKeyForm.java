package com.key.SSL_Cert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class ImportKeyForm extends FormLayout {
    final TextField storeName_tf = new TextField("Keystore file name");
    final PasswordField storePwd_pf = new PasswordField("Keystore password");
    final Button generate_btn = new Button("Generate", this::generateKeyfile);
	final ComboBox spcFile_cb = new ComboBox("Keystore file");
    
    private static final String ERROR_MATCH = "Passwords must match";
    private static final String ERROR_EMPTY = " must be inputed.";
    
    public ImportKeyForm()
    {
    	configureComponents();
    	validateComponents();
    	buildLayout();
    }
    
    private void configureComponents()
    {
		String str = System.getProperty("user.dir");
		spcFile_cb.addItems(getKeyFiles(str));
		spcFile_cb.setImmediate(true);
    	
    	storeName_tf.setInputPrompt("ex. codesignstore");
    	storeName_tf.setImmediate(true);
        
        generate_btn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        generate_btn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        
        setMargin(true);
    }
    
    private void validateComponents()
    {
    	spcFile_cb.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code == null || code.length() == 0)
				{
					throw new InvalidValueException("You must select a spc file");
				}
			}
		});
    	
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
    }
    
    private void buildLayout()
    {
    	addComponents(spcFile_cb, storeName_tf, storePwd_pf, generate_btn);
    }

	private List<String> getKeyFiles(String rootpath)
	{
		List<String> keyFiles = new ArrayList<>();
		final File subdir = new File(rootpath/* + "/keys"*/); 
		final File[] files = subdir.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length; i ++)
		{
			if (files[i].isDirectory() || files[i].getName().indexOf(".spc") == -1) continue;
			keyFiles.add(files[i].getName());
		}
		return keyFiles;
	}

    public void generateKeyfile(Button.ClickEvent event)
    {
    	try {
    		spcFile_cb.validate();
    		storeName_tf.validate();
    		String execStr = String.format("keytool -importcert -trustcacerts -keystore %s -storepass %s -alias codesigncert -file %s -noprompt", storeName_tf.getValue(), storePwd_pf.getValue(), spcFile_cb.getValue());
    		Process testProc = Runtime.getRuntime().exec(execStr);
    		Notification.show("Success");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//Notification.show(E);
		}
    }
}
