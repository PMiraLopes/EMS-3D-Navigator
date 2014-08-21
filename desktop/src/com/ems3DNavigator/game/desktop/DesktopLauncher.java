package com.ems3DNavigator.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ems3DNavigator.manager.Ems3DNavigator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "3D Builidings EMS";
        config.height = 800;
        config.width = 1280;
		new LwjglApplication(new Ems3DNavigator(), config);
	}
}
