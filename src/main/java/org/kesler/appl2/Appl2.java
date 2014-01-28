package org.kesler.appl2;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

import org.kesler.appl2.gui.main.MainViewController;
import org.kesler.appl2.util.OptionsUtil;

public class Appl2 {

	public static void main(String[] args) {
		
		OptionsUtil.readOptions();
		WebLookAndFeel.install();
		AppStarter starter = new AppStarter();
		SwingUtilities.invokeLater(starter);
	}

}

class AppStarter extends Thread {
	public void run() {
		MainViewController.getInstance().openMainView();
	}
}