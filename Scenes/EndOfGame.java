package Scenes;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Data.Scoredata;
import main.CreateScene;
import main.Main;

@SuppressWarnings("serial")
public final class EndOfGame extends Scene {

	public EndOfGame(String soundFile, String imageFile) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (Scoredata.isItBetter()) {
					JPanel jpHighscore = new JPanel();
					jpHighscore.setLayout(new GridLayout(1, 2));
					JLabel jlName = new JLabel("Name eingeben: ");
					JTextField jtfName = new JTextField(10);
					String[] options = {"OK"};
					jpHighscore.add(jlName);
					jpHighscore.add(jtfName);
					
					int value = JOptionPane.showOptionDialog(null, jpHighscore, "NEW Highscore!!!", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("Images/Icon1.png"), options ,null);

					if (value == JOptionPane.OK_OPTION) {
						Scoredata.saveNewScore(jtfName.getText());
						Scoredata.speichern();
					}

				} else {
					Scoredata.score=0;
					JOptionPane.showMessageDialog(null, "Sorry.Better luck next time!", "You Lose", JOptionPane.ERROR_MESSAGE);
				}
				Main.MAIN.changeScene(CreateScene.createHighscore());
			}
		});
	}
}
