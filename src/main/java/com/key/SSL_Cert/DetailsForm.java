package com.key.SSL_Cert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

public class DetailsForm extends FormLayout {
	ComboBox select = new ComboBox("Select a file");
    final PasswordField storePwd_pf = new PasswordField("Keystore password");
    final Button generate_btn = new Button("Analyze", this::analyzeKeyFile);
    final TextArea result_ta = new TextArea("Result");

    private static final String ERROR_EMPTY = " must be inputed.";
	
	public DetailsForm()
	{
		configureComponents();
		validateComponents();
		addComponents(select, storePwd_pf, generate_btn, result_ta);
        setMargin(true);
	}
    
    private void validateComponents()
    {
    	select.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				// TODO Auto-generated method stub
				String code = (String)value;
				if (code == null || code.length() == 0)
				{
					throw new InvalidValueException("You must select a keystore file");
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
	
    private void configureComponents()
    {
		String str = System.getProperty("user.dir");
		select.addItems(getKeyFiles(str));

		result_ta.setRows(14);
		result_ta.setWordwrap(false);
        result_ta.setSizeFull();

        generate_btn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        generate_btn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        generate_btn.setImmediate(true);
    }

	private List<String> getKeyFiles(String rootpath)
	{
		List<String> keyFiles = new ArrayList<>();
		final File subdir = new File(rootpath/* + "/keys"*/); 
		final File[] files = subdir.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length; i ++)
		{
			if (files[i].isDirectory() || files[i].getName().indexOf(".") > -1) continue;
			keyFiles.add(files[i].getName());
		}
		return keyFiles;
	}
	
    public void analyzeKeyFile(Button.ClickEvent event)
    {
    	try {
    		select.validate();

    		String execStr = String.format("keytool -list -v -keystore %s -storepass %s", select.getValue(), storePwd_pf.getValue());
    		Process testProc = Runtime.getRuntime().exec(execStr);

    		BufferedReader input = new BufferedReader(new InputStreamReader(testProc.getInputStream()));

			result_ta.setValue("");
			String line = null;
			
			while ((line = input.readLine()) != null)
			{
				//System.out.println(line);
				result_ta.setValue(result_ta.getValue() + line + "\n");
			}
			//result_ta.setCaption(result);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//Notification.show(E);
		}
    }
}
