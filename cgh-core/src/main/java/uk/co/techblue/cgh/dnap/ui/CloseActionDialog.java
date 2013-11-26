package uk.co.techblue.cgh.dnap.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import uk.co.techblue.cgh.dnap.util.UIHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class CloseActionDialog.
 * 
 * @author dheeraj
 */
public class CloseActionDialog extends Dialog {

    // private Button btnDontPromptMe;
    /** The btn exit the program. */
    private Button btnExitTheProgram;

    /** The btn minimize to system. */
    private Button btnMinimizeToSystem;

    /** The minimize to tray. */
    private boolean minimizeToTray = false;

    // private boolean dontShowAgain = false;

    /**
     * Instantiates a new close action dialog.
     * 
     * @param shell the shell
     * @param toggleState the toggle state
     */
    protected CloseActionDialog(Shell shell, boolean toggleState) {
        super(shell);
        this.minimizeToTray = toggleState;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(final Composite parent) {
        Composite cmpBody = (Composite) super.createDialogArea(parent);
        cmpBody.setLayout(new GridLayout(1, false));
        new Label(cmpBody, SWT.NONE);

        Label lblCloseButtonAction = new Label(cmpBody, SWT.NONE);
        lblCloseButtonAction.setText("Close button action?");

        Composite cmpRadioControls = new Composite(cmpBody, SWT.NONE);
        cmpRadioControls.setLayout(new GridLayout(2, false));
        GridData gd_cmpRadioControls = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_cmpRadioControls.widthHint = 550;
        cmpRadioControls.setLayoutData(gd_cmpRadioControls);

        new Label(cmpRadioControls, SWT.NONE);

        btnMinimizeToSystem = new Button(cmpRadioControls, SWT.RADIO);
        btnMinimizeToSystem.setText("Minimize to system tray");
        btnMinimizeToSystem.setSelection(minimizeToTray);
        new Label(cmpRadioControls, SWT.NONE);

        btnExitTheProgram = new Button(cmpRadioControls, SWT.RADIO);
        btnExitTheProgram.setText("Exit the program");
        btnExitTheProgram.setSelection(!minimizeToTray);
        new Label(cmpBody, SWT.NONE);

        // btnDontPromptMe = new Button(cmpBody, SWT.CHECK);
        // btnDontPromptMe.setText("Don't show prompt me next time");
        UIHelper.setDialogLocation(getShell());
        return cmpBody;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    public void okPressed() {
        setMinimizeToTray(btnMinimizeToSystem.getSelection());
        // setDontShowAgain(btnDontPromptMe.getSelection());
        close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(final Shell shell) {
        if (SWT.getPlatform() == "win32") {
            shell.setSize(500, 190);
        } else if (SWT.getPlatform() == "gtk") {
            shell.setSize(530, 250);
        }
        super.configureShell(shell);
        shell.setText("Settings");
    }

    /**
     * Checks if is minimize to tray.
     * 
     * @return true, if is minimize to tray
     */
    public boolean isMinimizeToTray() {
        return this.minimizeToTray;
    }

    /**
     * Sets the minimize to tray.
     * 
     * @param minimizeToTray the new minimize to tray
     */
    public void setMinimizeToTray(final boolean minimizeToTray) {
        this.minimizeToTray = minimizeToTray;
    }

    // public boolean isDontShowAgain() {
    // return this.dontShowAgain;
    // }
    //
    // public void setDontShowAgain(final boolean dontShowAgain) {
    // this.dontShowAgain = dontShowAgain;
    // }

}
