/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list.ui.controllers;

import com.github.idelstak.shopping.list.model.Item;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 FXML Controller class

 @author Hiram K <hiram.kamau@outlook.com>
 */
public class ItemRowController {

    @FXML
    private Label noLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label unitLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label eachLabel;
    @FXML
    private Label totalLabel;

    /**
     Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // TODO
    }

    public void setItem(Item item) {
        noLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return Integer.toString(item.getId()) + ')';
        }, item.idProperty()));

        descriptionLabel.textProperty().bind(item.descriptionProperty());

        unitLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return item.getUnit().toString();
        }, item.unitProperty()));

        quantityLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return item.getQuantity().toString();
        }, item.quantityProperty()));

        eachLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return item.getEach().toString();
        }, item.eachProperty()));

        totalLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return item.getTotal().toString();
        }, item.totalProperty()));
    }

}
