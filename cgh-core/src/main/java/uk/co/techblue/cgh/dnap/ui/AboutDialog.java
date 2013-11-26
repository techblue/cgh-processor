package uk.co.techblue.cgh.dnap.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants;
import uk.co.techblue.cgh.dnap.util.UIHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AboutDialog.
 * @author dheeraj
 */
public class AboutDialog extends Dialog {

	/** The lbl about. */
	private CLabel lblAbout;
	
    /**
     * Instantiates a new about dialog.
     *
     * @param parentShell the parent shell
     */
    protected AboutDialog(Shell parentShell) {
		super(parentShell);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(final Composite parent) {
    	Composite cmpBody = (Composite) super.createDialogArea(parent);
    	cmpBody.setLayout(new GridLayout(1, false));
        lblAbout = new CLabel(cmpBody, SWT.NONE);
        GridData gd_lblDialog = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblDialog.heightHint = 140;
        lblAbout.setLayoutData(gd_lblDialog);
        lblAbout.setText(SignalProcessorConstants.DIALOG_TITLE+"\nVersion: 1.0.1 \n (c) Copyright Technology Blueprint 2013.  \nAll rights reserved");
        UIHelper.setDialogLocation(getShell());
		return cmpBody;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("About");
		shell.setSize(380, 198);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 */
	protected Button createButton(Composite arg0, int arg1, String arg2, boolean arg3){
		return null;
	}
}
