/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.ws.reddeer.ui.wizards.jaxrs;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.eclipse.selectionwizard.SelectionWizardOpenable;
import org.eclipse.reddeer.eclipse.ui.dialogs.NewWizard;
import org.eclipse.reddeer.eclipse.ui.dialogs.WorkbenchWizardSelectionPage;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.impl.button.NextButton;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuWizardDialog;
import org.hamcrest.Matcher;

/**
 * JAX-RS Application wizard.
 *
 * Web Services > JAX-RS Application
 *
 * Has only one wizard page - {@link JAXRSApplicationWizardPage}
 *
 * @author Radoslav Rabara
 * @since JBT 4.2.0 Beta2
 * @see http://tools.jboss.org/documentation/whatsnew/jbosstools/4.2.0.Beta2.html#webservices
 */
public class JAXRSApplicationWizard extends NewMenuWizard {
	
	public JAXRSApplicationWizard() {
		super("", "Web Services", "JAX-RS Application");
	}
	
	@Override
	public Openable getOpenAction() {
		return new JAXRSSelectionWizardOpenable(new JAXRSNewWizard(), wizardPath, matcher);
	}

	private class JAXRSNewWizard extends NewWizard {
		
		public JAXRSNewWizard() {
			super();
		}
		
		/**
		 * Click the next button in wizard dialog.
		 */
		@Override
		public WizardDialog next() {
			checkShell();
			log.info("Go to next wizard page");

			Button button = new NextButton(this);
			try {
				button.click();
			} catch (Exception exc) {
				System.out.println(exc.getLocalizedMessage());
			}
			String title = getTitle();
			if (title.equals("Select a wizard")) {
				button.click();
			}
			return this;
		}
	}
	
	private class JAXRSSelectionWizardOpenable extends Openable {

		private String[] wizardPath;
		private WorkbenchMenuWizardDialog selectionWizard;

		public JAXRSSelectionWizardOpenable(WorkbenchMenuWizardDialog selectionWizard, String[] wizardPath, Matcher<?> shellMatcher) {
			super(shellMatcher);
			this.wizardPath = wizardPath;
			this.selectionWizard = selectionWizard;
		}

		@Override
		public void run() {
			selectionWizard.open();
			WorkbenchWizardSelectionPage selectionPage = new WorkbenchWizardSelectionPage(selectionWizard){};
			selectionPage.selectProject(wizardPath);
			selectionWizard.next();
		}
	}
}
