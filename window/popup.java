package window;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;


public class popup extends JFrame implements ActionListener
{
	private JLabel message;
	private JButton valider = new JButton("Ok");
	private JPanel panneau=new JPanel();

	public popup(String mess)	
	{
		valider.addActionListener(this);

		this.message = new JLabel(mess);

		this.setSize(200,100);
		this.setResizable(false);

		panneau.add(message);
		panneau.add(valider);

		this.setContentPane(panneau);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==valider)
		{
			this.dispose();
		}
	}
}
