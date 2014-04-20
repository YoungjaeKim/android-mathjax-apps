package com.thirteencubes.prepup.android.core;


import android.app.Activity;
import android.os.Bundle;

import com.thirteencubes.prepup.android.R;

public class ExamSelectionActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_selection_activity);
		
		//PhoneContact pCon = new PhoneContact(context);
       // List<Contact> conList = pCon.getAllContacts();

        //HashMap<Integer, String> phones = conList.get(0).getPhones();
        //String home = phones.get(Contact.PHONE_TYPE.HOME);
	}

}
