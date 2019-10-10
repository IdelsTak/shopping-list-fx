/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list.ui;

import com.github.idelstak.shopping.list.model.Item;
import com.github.idelstak.shopping.list.ui.controllers.ItemRowController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**

 @author Hiram K <hiram.kamau@outlook.com>
 */
public class ItemNode implements Lookup.Provider {

    private static final Logger LOG = Logger.getLogger(ItemNode.class.getName());
    private final Lookup lookup;

    public ItemNode(Item item) {
        this(item, new InstanceContent());
    }

    private ItemNode(Item item, InstanceContent content) {
        lookup = new AbstractLookup(content);
        content.add(item);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ItemRow.fxml"));
            HBox box = loader.<HBox>load();
            ItemRowController controller = loader.<ItemRowController>getController();
            
            controller.setItem(item);
            content.add(box);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

}
