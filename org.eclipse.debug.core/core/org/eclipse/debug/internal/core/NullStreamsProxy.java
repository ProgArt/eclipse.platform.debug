/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.internal.core;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy2;

public class NullStreamsProxy implements IStreamsProxy2 {
    private NullStreamMonitor outputStreamMonitor;
    private NullStreamMonitor errorStreamMonitor;

    public NullStreamsProxy(Process process) {
        outputStreamMonitor = new NullStreamMonitor(process.getInputStream());
        errorStreamMonitor = new NullStreamMonitor(process.getErrorStream());
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.model.IStreamsProxy2#closeInputStream()
     */
    @Override
	public void closeInputStream() throws IOException {
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.model.IStreamsProxy#getErrorStreamMonitor()
     */
    @Override
	public IStreamMonitor getErrorStreamMonitor() {
        return errorStreamMonitor;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.model.IStreamsProxy#getOutputStreamMonitor()
     */
    @Override
	public IStreamMonitor getOutputStreamMonitor() {
        return outputStreamMonitor;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.model.IStreamsProxy#write(java.lang.String)
     */
    @Override
	public void write(String input) throws IOException {
    }

    private class NullStreamMonitor implements IStreamMonitor {
        private InputStream fStream;

        public NullStreamMonitor(InputStream stream) {
            fStream = stream;
            startReaderThread();
        }

        private void startReaderThread() {
			Thread thread = new Thread((Runnable) () -> {
				byte[] bytes = new byte[1024];
				try {
					while (fStream.read(bytes) >= 0) {
						// do nothing
					}
				} catch (IOException e) {
				}
			}, DebugCoreMessages.NullStreamsProxy_0);
            thread.setDaemon(true);
            thread.start();

        }

        /* (non-Javadoc)
         * @see org.eclipse.debug.core.model.IStreamMonitor#addListener(org.eclipse.debug.core.IStreamListener)
         */
        @Override
		public void addListener(IStreamListener listener) {
        }

        /* (non-Javadoc)
         * @see org.eclipse.debug.core.model.IStreamMonitor#getContents()
         */
        @Override
		public String getContents() {
            return ""; //$NON-NLS-1$
        }

        /* (non-Javadoc)
         * @see org.eclipse.debug.core.model.IStreamMonitor#removeListener(org.eclipse.debug.core.IStreamListener)
         */
        @Override
		public void removeListener(IStreamListener listener) {
        }
    }
}
