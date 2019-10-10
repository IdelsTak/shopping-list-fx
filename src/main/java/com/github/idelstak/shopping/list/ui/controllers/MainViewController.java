/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list.ui.controllers;

import com.github.idelstak.shopping.list.api.ItemsRepository;
import com.github.idelstak.shopping.list.model.Item;
import com.github.idelstak.shopping.list.ui.ItemNode;
import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 FXML Controller class

 @author Hiram K <hiram.kamau@outlook.com>
 */
public class MainViewController {

    private static final Logger LOG = Logger.getLogger(MainViewController.class.getName());
    private static final ItemsRepository ITEMS_REPO = ItemsRepository.getDefault();
    @FXML
    private VBox rootBox;
    @FXML
    private HBox headerBox;
    @FXML
    private VBox itemsBox;
    @FXML
    private HBox addActionBox;
    @FXML
    private Button addItemButton;

    /**
     Initializes the controller class.
     */
    @FXML
    public void initialize() {
        ITEMS_REPO.addListener((Change<? extends Item> change) -> {
            LOG.log(Level.FINE, "Change occured: {0}", change);

            if (change.wasAdded()) {
                Platform.runLater(() -> {
                    int size = rootBox.getChildren().size();
                    ItemNode node = new ItemNode(change.getElementAdded());
                    HBox box = node.getLookup().lookup(HBox.class);
                    itemsBox.getChildren().add(box);
                });
            }
        });

        addItemButton.setOnAction(e -> {
            int id = ITEMS_REPO.count()+1;
            com.github.javafaker.Number number = new Faker().number();
            Commerce commerce = new Faker().commerce();
            BigDecimal qty = BigDecimal.valueOf(Double.valueOf(Integer.toString(number.numberBetween(1, 15))));
            BigDecimal each = BigDecimal.valueOf(Double.parseDouble(commerce.price(10.0, 1000.0)));
            Item item = new Item(id, commerce.productName(), Item.Unit.PC, qty, each);
            
            ITEMS_REPO.add(item);
        });
    }

}
