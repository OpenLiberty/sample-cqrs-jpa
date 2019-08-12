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
package io.openliberty.guides.music.ui;

import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;

import io.openliberty.guides.music.ui.facelets.PageDispatcher;
import io.openliberty.guides.music.ui.util.TimeMapUtil;
import io.openliberty.guides.music.client.QueryClient;
 import io.openliberty.guides.music.client.CommandClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.openliberty.guides.music.client.UnknownUrlException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.annotation.ManagedProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ViewScoped
public class MusicBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String artist;
    private String price;
    private String likes;
    private ComponentSystemEvent currentComponent;
    private int selectedId;
    private boolean notValid;

    // @Inject
    // @RestClient
    // private MusicClient musicClient;
    @Inject
    @RestClient
    private QueryClient queryClient;
    
     @Inject
     @RestClient
     private CommandClient commandClient;

    @Inject
    @ManagedProperty(value = "#{pageDispatcher}")
    private PageDispatcher pageDispatcher;

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getPrice() {
        return this.price;
    }

    public String getLikes() {
        return this.likes;
    }

    public boolean getNotValid() {
        return notValid;
    }

    public PageDispatcher getPageDispatcher() {
        return this.pageDispatcher;
    }

    public void setPageDispatcher(PageDispatcher pageDispatcher) {
        this.pageDispatcher = pageDispatcher;
    }

    public void setCurrentComponent(ComponentSystemEvent component) {
        this.currentComponent = component;
    }

    /**
     * Set a selected song id.
     */
    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    /**
     * Remove stored song id.
     */
    public void removeSelectedId() {
        this.selectedId = -1;
    }


    /**
     * Mapped the time string retrieved from back end service to a user readable
     * format.
     */
    public String showTime(String storedTime) {
        return TimeMapUtil.getMappedDate(storedTime);
    }

    public void printValues() {
        System.out.println(name);
        System.out.println(artist);
        System.out.println(price);
    }

    /**
     * Submit song form data to back end service.
     */
    public void submitToService() {
        // String time = createStoredTime();
        try {
             commandClient.addSong(name, artist, price);
            pageDispatcher.showMainPage();
            clear();
       } catch (UnknownUrlException e) { 
            System.out.println("catch unknown url");
            System.err.println("The given URL is unreachable.");
        } catch (BadRequestException e) {
            System.out.println("catch badrequest exception");
            displayInvalidEventError();
        }

    }

    /**
     * Submit updated song form data to back end service.
     */
    public void submitUpdateToService() {
        // String time = createStoredTime();
        try {
            commandClient.updateSong(this.name, this.artist, this.price, this.selectedId);
            pageDispatcher.showMainPage();
            clear();
        } catch (UnknownUrlException e) {
            System.err.println("The given URL is unreachable");
        } catch (BadRequestException e) {
            displayInvalidEventError();
        }
    }
    
    /**
     * Submit addLike form data to back end service.
     */
    public void submitAddLike(){
    	try{
    		commandClient.addLike(this.selectedId);
    		pageDispatcher.showMainPage();
            clear();
    	} catch (UnknownUrlException e) {
            System.err.println("The given URL is unreachable");
        } catch (BadRequestException e) {
            displayInvalidEventError();
        }
    }

    public void editSong() {
        JsonObject song = retrieveSongById(this.selectedId);
        this.name = song.getString("name");
        this.artist = song.getString("artist");
        this.price = song.getString("price");
         this.likes = song.getString("likes");

        pageDispatcher.showEditPage();
    }

    /**
     * Delete event form data to back end service.
     */
    public void submitDeletetoService() {
        try {
             commandClient.deleteSong(this.selectedId);
        } catch (UnknownUrlException e) {
            System.err.println("The given URL is unreachable");
        }

        pageDispatcher.showMainPage();
    }

    /**
     * Retrieve the list of songs from back end service.
     */
    public JsonArray retrieveEventList() {
        try {
            return queryClient.getSongs();
        } catch (UnknownUrlException e){
            System.err.println("The given URL is unreachable.");
            return null;
        }
    }


    /**
     * Retrieve the list of songs from back end service.
     */
    public JsonArray retrieveSongList() {
         try {
             return queryClient.getSongs();
         } catch (UnknownUrlException e){
             System.err.println("The given URL is unreachable.");
             return null;
         }
    }

    /**
     * Retrieve a selected song by Id
     */
    public JsonObject retrieveSongById(int selectedId) {
        try {
            return queryClient.getSong(selectedId);
        } catch (UnknownUrlException e) {
            System.err.println("The given URL is unreachable");
            return null;
        }
    }

    /**
     * Gets TimeMapUtil map for hours
     */
    public Map<String, Object> getHoursMap() {
        return TimeMapUtil.getHours();
    }

    /**
     * Gets TimeMapUtil map for days
     */
    public Map<String, Object> getDaysMap() {
        return TimeMapUtil.getDays();
    }

    /**
     * Gets TimeMapUtil map for months
     */
    public Map<String, Object> getMonthsMap() {
        return TimeMapUtil.getMonths();
    }

    /**
     * Gets TimeMapUtil map for years
     */
    public Map<String, Object> getYearsMap() {
        return TimeMapUtil.getYears();
    }

    /**
     * Checks the user input for the event time.
     */
    public void checkTime(ComponentSystemEvent event) throws ParseException {
        String hour = getUnitOfTime(event, "eventHour");
        String day = getUnitOfTime(event, "eventDay");
        String month = getUnitOfTime(event, "eventMonth");
        String year = getUnitOfTime(event, "eventYear");

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a, MMMM d yyyy");
        formatter.setLenient(false);
        Date userDate;

        try {
            userDate = formatter.parse(hour + ", " + month + " " + day + " " + year);
            if (userDate.before(new Date())) {
                allowSubmission(event, false);
                addErrorMessage(event, "Choose a valid time");
                displayError(true);
            } else {
                allowSubmission(event, true);
                displayError(false);
            }
        } catch (Exception e) {
            allowSubmission(event, false);
            addErrorMessage(event, "Choose a valid time");
            displayError(true);
        }
    }

    /**
     * Displays the error message if time is not valid or the song already exists
     */
    public void displayError(boolean display) {
        notValid = display;
    }

    /**
     *  Method to clean the bean after form submission and before song creation form
     */
    public void clear() {
        setName(null);
        setArtist(null);
        setPrice(null);
        setLikes(null);
    }

    /**
     * Helper method to create the time string to be stored at the back end.
     */
    // private String createStoredTime() {
    //     return hour + ", " + month + " " + day + " " + year;
    // }

    /**
     * Parses time (in format: hh:mm AM, dd mm yyyy) into time, meridiem, month,
     * day, year respectively.
     */
    private String[] parseTime(String time) {
        String delims = "[ ,]+";
        return time.split(delims);
    }

    /**
     * Adds "Choose a valid time" message after selectOptions in user interface.
     */
    private void addErrorMessage(ComponentSystemEvent event, String errorMessage) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(errorMessage);
        HtmlPanelGroup divEventTime = (HtmlPanelGroup) event.getComponent()
            .findComponent("music:eventTime");
        context.addMessage(divEventTime.getClientId(context), message);
    }

    /**
     * Gets 'selectOptions' for a specific unit of time from user interface
     */
    private UIInput getUnitOfTimeOptions(ComponentSystemEvent event, String unit) {
        UIComponent components = event.getComponent();
        UIInput unitOptions = (UIInput) components.findComponent("musicform:" + unit);
        return unitOptions;
    }

    /**
     * Gets a unit of time from the submitted values, like hour and year
     */
    private String getUnitOfTime(ComponentSystemEvent event, String unit) {
        UIInput unitOptions = getUnitOfTimeOptions(event, unit);
        return (String) unitOptions.getLocalValue();
    }

    /**
     * Allow if user can submit or not.
     */
    private void allowSubmission(ComponentSystemEvent event, boolean allowSubmission) {
        UIComponent components = event.getComponent();
        HtmlInputHidden formInput = (HtmlInputHidden) components
            .findComponent("musicform:eventSubmit");
        formInput.setValid(allowSubmission);
    }

    /**
     *  Display error message if Event already exists and don't allow form submission
     */
    private void displayInvalidEventError() {
        allowSubmission(currentComponent, false);
        addErrorMessage(currentComponent, "Event already exists!");
        displayError(true);
    }
}
