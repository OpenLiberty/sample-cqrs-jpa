// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.music.ui.facelets;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;

@Named
@SessionScoped
public class PageDispatcher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @ManagedProperty(value = "#{pageLoader}")
    private PageLoader pageLoader;

    String currentRole = null;

    public PageLoader getPageLoader() {
        return pageLoader;
    }

    public void setPageLoader(PageLoader pageLoader) {
        this.pageLoader = pageLoader;
    }

    public void showSongForm() {
        System.out.println("Showing song form");
        pageLoader.setContent("content/songForm.xhtml");
        pageLoader.setCurrentPage("Song Creation");
    }

    public void showMainPage() {
        System.out.println("Showing main page");
        pageLoader.setContent("content/mainPage.xhtml");
        pageLoader.setCurrentPage("Music");
    }

    public void showEditPage() {
        System.out.println("Showing edit page");
        pageLoader.setContent("content/updateSongForm.xhtml");
        pageLoader.setCurrentPage("Edit Song");
    }
    
    public void showTopTen() {
        pageLoader.setContent("content/topTenDisplay.xhtml");
        pageLoader.setCurrentPage("Top 10 Songs");
    }
}
