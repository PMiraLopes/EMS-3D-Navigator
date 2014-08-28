package com.ems3DNavigator.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ems3DNavigator.app.Ems3DNavigator;


// TODO: Auto-generated Javadoc
/**
 * The Class AndroidLauncher.
 */
public class AndroidLauncher extends AndroidApplication {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Ems3DNavigator(), config);
	}
}
