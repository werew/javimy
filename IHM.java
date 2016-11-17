import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.*;

public class IHM
{
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setTitle("Filtre");
		window.setSize(500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menu=new JMenuBar();
		window.setJMenuBar(menu);

		JMenu file=new JMenu("Fichier");
		menu.add(file);

		window.setVisible(true);

		JFileChooser fichier=new JFileChooser(new File("."));
		int boite=fichier.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as choisi: "+fichier.getSelectedFile());
		}
		else
		{
			System.out.println("Perdu");
		}

	}
}
