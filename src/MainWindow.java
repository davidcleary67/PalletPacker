import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Choices for matching numbers of bags and total value.
 * 
 * @author devops20
 *
 */
enum ECheck {
	MATCH_YES,
	MATCH_NO,
	MATCH_ERROR
}

/**
 * Displays a window into which the user enters the number of large (5 kg) and small (1 kg) bags 
 * that are available and the total volume of goods to be loaded onto a pallet.
 * <p>
 * When the user clicks on [Check], the windows displays whether or not the numbers of bags 
 * and the volume of good match.
 * <p>
 * Clicking on [Close] closes the application.
 * 
 * @author David Cleary
 *
 */
public class MainWindow {
	
	String stTitle;
	
	/**
	 * User specified number of large and small bags and total value.
	 */
	JTextField txLarge;
	JTextField txSmall;
	JTextField txVolume;
	
	JLabel lbMatch;

	/** 
	 * Constructor
	 */
	public MainWindow() {
		stTitle = "Pallet Packer";
		CreateWindow();
	}

	/**
	 * Create the Pallet packer window.
	 */
	private void CreateWindow()	{
		
		// Windows frame
		JFrame frame = new JFrame(stTitle);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(400, 160);
		
		// Panels
		// Panel -> Large bags field
		JPanel plLarge = new JPanel(new BorderLayout());
		JLabel lbLarge = new JLabel("Number large (5 kg) bags:");
		txLarge = new JTextField();
		plLarge.setLayout(new BoxLayout(plLarge, BoxLayout.X_AXIS));
		plLarge.add(lbLarge);
		plLarge.add(txLarge);
		plLarge.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		// Panel -> Small bags field
		JPanel plSmall = new JPanel(new BorderLayout());
		JLabel lbSmall = new JLabel("Number small (1 kg) bags:");
		txSmall = new JTextField();
		plSmall.setLayout(new BoxLayout(plSmall, BoxLayout.X_AXIS));
		plSmall.add(lbSmall);
		plSmall.add(txSmall);
		plSmall.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		// Panel -> Volume field
		JPanel plVolume = new JPanel(new BorderLayout());
		JLabel lbVolume = new JLabel("Total volume (kg):");
		txVolume = new JTextField();
		plVolume.setLayout(new BoxLayout(plVolume, BoxLayout.X_AXIS));
		plVolume.add(lbVolume);
		plVolume.add(txVolume);
		plVolume.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		// Panel -> All fields
		JPanel plFields = new JPanel();
		plFields.setLayout(new BoxLayout(plFields, BoxLayout.Y_AXIS));
		lbMatch = new JLabel("Enter values and click [Check].");
		plFields.add(plLarge);
		plFields.add(plSmall);
		plFields.add(plVolume);
		plFields.add(lbMatch);
		
		// Panel -> Buttons
		JPanel plButtons = new JPanel();
        JButton btCheck = new JButton("Check");
        JButton btClose = new JButton("Close");
        plButtons.add(btCheck);
        plButtons.add(btClose);
        
        // Add main panels to frame
        frame.getContentPane().add(BorderLayout.SOUTH, plButtons);
        frame.getContentPane().add(BorderLayout.CENTER, plFields);
        
        // Action listeners
        
        // Action listener -> Close
        ActionListener alClose = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OkCancel("Do you wish to close the Pallet Packer?", "Confirm Exit")) {
					System.exit(0);
				}
			}
		};
		btClose.addActionListener(alClose);
		
        // Action listener -> Check
        ActionListener alCheck = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (CheckBags()) {
					case MATCH_YES:
						lbMatch.setText("Bags and volume match!");
						break;
					case MATCH_NO:
						lbMatch.setText("Bags and volume NO NOT match!");
						break;
					case MATCH_ERROR:
						lbMatch.setText("Error reading bags and volume!");
						break;
				}
			}
		};
		btCheck.addActionListener(alCheck);

		// Display frame
        frame.setVisible(true);
	}

	/**
	 * Compare the numbers of large and small bags against the total volume.
	 * 
	 * @return <code>MATCH_YES</code> if the total volume can be packed into the bags
	 * 		   <code>MATCH_NO</code> if the total volume can not be packed into the bags
	 *         <code>MTACH_ERROR</code> if the user specified values can not be interpreted as integers 
	 */
	public ECheck CheckBags() {
		
		try {
			Integer iLarge = Integer.parseInt(txLarge.getText());
			Integer iSmall = Integer.parseInt(txSmall.getText());
			Integer iVolume = Integer.parseInt(txVolume.getText());
			Integer iLargeBy5 = iLarge * 5;
			ECheck eResult = ECheck.MATCH_NO;
			
			if (iLargeBy5 >= iVolume) {
				if (iSmall >= (iVolume % 5)) {
					eResult = ECheck.MATCH_YES;
				}
			}
			else {
				if (iSmall >= (iVolume - iLargeBy5)) {
					eResult = ECheck.MATCH_YES;
				}
			}
			return eResult;
		}
		catch (Exception e) {
			return ECheck.MATCH_ERROR;
		}
	}
		
	/**
	 * Display an OK/Cancel dialog box.
	 * @param stMessage	message to display
	 * @param stTitle	title to display
	 * @return <code>true</code> if [OK] is selected
	 * 		   <code>false</code> if [Cancel] is selected
	 */
	public static boolean OkCancel(String stMessage, String stTitle) {
		return (JOptionPane.showConfirmDialog((Component)null, stMessage, stTitle, JOptionPane.OK_CANCEL_OPTION) == 0) ? true : false;
	}
	
}