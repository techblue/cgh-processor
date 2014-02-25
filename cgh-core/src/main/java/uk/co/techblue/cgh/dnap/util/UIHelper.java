package uk.co.techblue.cgh.dnap.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class UIHelper.
 * @author dheeraj
 */
public class UIHelper {

    /**
     * Show.
     *
     * @param parent the parent
     */
    public static void show(final Composite parent) {
        Shell sh = parent.getShell();
        if (sh.getLayout() == null)
            sh.setLayout(new FillLayout());

        parent.setParent(sh);
        sh.pack();
        sh.layout();
        Point size = sh.computeSize(-1, -1);
        Rectangle screen = sh.getDisplay().getMonitors()[0].getClientArea();
        if (size.x > screen.width)
            size.x = screen.width;
        if (size.y > screen.height)
            size.y = screen.height;
        sh.setBounds((screen.width - size.x) / 2, (screen.height - size.y) / 2, size.x, size.y);
        sh.open();

        while (!sh.isDisposed()) {
            if (!sh.getDisplay().readAndDispatch())
                sh.getDisplay().sleep();
        }
        if (!sh.isDisposed())
            sh.close();
    }
    
    /**
     * Creates the header.
     *
     * @param container the container
     * @param headerText the header text
     * @return the composite
     */
    public static Composite createHeader(final Composite container, String headerText) {
        Composite cmpHeader = new Composite(container, SWT.NONE);
        cmpHeader.setBackground(container.getBackground());
        cmpHeader.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout headerLayout = new GridLayout();
        cmpHeader.setLayout(headerLayout);

        Label lblHeader = new Label(cmpHeader, SWT.CENTER);
        lblHeader.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.ITALIC));
        lblHeader.setBackground(cmpHeader.getBackground());
        lblHeader.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        lblHeader.setText(headerText);
        
        return cmpHeader;
    }
    
    /**
     * Creates the footer.
     *
     * @param container the container
     * @return the composite
     */
    public static Composite createFooter(final Composite container) {
        Composite grpModalControls = new Composite(container, SWT.BORDER);
        grpModalControls.setBackground(container.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        grpModalControls.setLayoutData(layoutData);
        grpModalControls.setLayout(new GridLayout());

        Composite cmpFooter = new Composite(grpModalControls, SWT.NONE);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.END;
        data.grabExcessHorizontalSpace = true;
        cmpFooter.setLayoutData(data);
        RowLayout layout = new RowLayout();
        layout.spacing = 20;
        layout.marginBottom = 30;
        cmpFooter.setLayout(layout);
        cmpFooter.setBackground(grpModalControls.getBackground());
        
        return cmpFooter;
    }
    
    /**
     * Sets the dialog location.
     *
     * @param shell the new dialog location
     */
    public static void setDialogLocation(final Shell shell) {
		Rectangle monitorArea = shell.getDisplay().getPrimaryMonitor()
				.getBounds();
		Rectangle shellArea = shell.getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width) / 2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height) / 2;
		shell.setLocation(x, y);
	}

}
