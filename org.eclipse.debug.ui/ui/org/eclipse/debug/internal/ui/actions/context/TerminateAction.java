/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.ui.actions.context;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.internal.ui.DebugPluginImages;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.internal.ui.actions.ActionMessages;
import org.eclipse.debug.internal.ui.actions.provisional.IAsynchronousTerminateAdapter;
import org.eclipse.debug.internal.ui.actions.provisional.IBooleanRequestMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

public class TerminateAction extends AbstractDebugContextAction {
	
	public TerminateAction() {
		setActionDefinitionId("org.eclipse.debug.ui.commands.Terminate"); //$NON-NLS-1$
	}

    protected void doAction(final Object element) {
        if (element instanceof IAdaptable) {
            IAsynchronousTerminateAdapter adapter = (IAsynchronousTerminateAdapter) ((IAdaptable)element).getAdapter(IAsynchronousTerminateAdapter.class);
            if (adapter != null)
                adapter.terminate(element, new ActionRequestMonitor());
        }
    }



    protected void isEnabledFor(Object element, IBooleanRequestMonitor monitor) {
        if (element instanceof IAdaptable) {
            IAsynchronousTerminateAdapter adapter = (IAsynchronousTerminateAdapter) ((IAdaptable)element).getAdapter(IAsynchronousTerminateAdapter.class);
            if (adapter != null) {
                adapter.canTerminate(element, monitor);
            } else {
            	notSupported(monitor);
            }
        }
    }

    protected String getStatusMessage() {
        return ActionMessages.TerminateActionDelegate_Exceptions_occurred_attempting_to_terminate__2;
    }

    protected String getErrorDialogMessage() {
        return ActionMessages.TerminateActionDelegate_Terminate_failed__1;
    }

    public String getText() {
        return ActionMessages.TerminateAction_0;
    }

    public String getHelpContextId() {
        return "terminate_action_context"; //$NON-NLS-1$
    }

    public String getId() {
        return "org.eclipse.debug.ui.debugview.toolbar.terminate"; //$NON-NLS-1$
    }

    public String getToolTipText() {
        return ActionMessages.TerminateAction_3;
    }

    public ImageDescriptor getDisabledImageDescriptor() {
        return DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_TERMINATE);
    }

    public ImageDescriptor getHoverImageDescriptor() {
        return DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_LCL_TERMINATE);
    }

    public ImageDescriptor getImageDescriptor() {
        return DebugPluginImages.getImageDescriptor(IInternalDebugUIConstants.IMG_LCL_TERMINATE);
    }
}