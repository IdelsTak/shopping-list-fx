/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list.api;

import com.github.idelstak.shopping.list.model.Item;
import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.openide.util.Lookup;

/**

 @author Hiram K <hiram.kamau@outlook.com>
 */
public abstract class ItemsRepository {

    private static final Logger LOG = Logger.getLogger(ItemsRepository.class.getName());
    private static final Lookup LOOKUP = Lookup.getDefault();
    private static final SimpleItemsRepo DEFAULT = new SimpleItemsRepo();

    public static ItemsRepository getDefault() {
        return Optional.ofNullable(LOOKUP.lookup(ItemsRepository.class)).orElse(DEFAULT);
    }

    public abstract boolean add(Item item);

    public abstract int count();

    public abstract boolean remove(Item item);

    public abstract ObservableSet<Item> getAll();

    public abstract void addListener(SetChangeListener<? super Item> listener);

    public abstract void removeListener(SetChangeListener<? super Item> listener);

    private static class SimpleItemsRepo extends ItemsRepository {

        private final ObservableSet<Item> items;

        private SimpleItemsRepo() {
            items = FXCollections.observableSet(new HashSet<>());

            Service<Set<Item>> service = new Service<Set<Item>>() {
                @Override
                protected Task<Set<Item>> createTask() {
                    return new Task<Set<Item>>() {
                        @Override
                        protected Set<Item> call() throws Exception {
                            Set<Item> set = new HashSet<>();
                            IntStream intStream = IntStream.rangeClosed(1, 10);
                            Number number = new Faker().number();
                            Commerce commerce = new Faker().commerce();

                            intStream.forEach(i -> {
                                BigDecimal qty = BigDecimal.valueOf(Double.valueOf(Integer.toString(number.numberBetween(1, 15))));
                                BigDecimal each = BigDecimal.valueOf(Double.parseDouble(commerce.price(10.0, 1000.0)));
                                set.add(new Item(i, commerce.productName(), Item.Unit.PC, qty, each));
                            });

                            return set;
                        }
                    };
                }
            };

            service.setOnSucceeded(e -> {
                LOG.log(Level.FINE, "++++++Succeeded++++++");

                @SuppressWarnings("unchecked")
                Set<Item> value = (Set<Item>) e.getSource().getValue();
                value.stream().sorted().forEach(items::add);
            });

            service.start();
        }

        @Override
        public synchronized int count() {
            return items.size();
        }

        @Override
        public synchronized boolean add(Item item) {
            return items.add(item);
        }

        @Override
        public synchronized boolean remove(Item item) {
            return items.remove(item);
        }

        @Override
        public synchronized ObservableSet<Item> getAll() {
            return FXCollections.unmodifiableObservableSet(items);
        }

        @Override
        public synchronized void addListener(SetChangeListener<? super Item> listener) {
            items.addListener(listener);
        }

        @Override
        public synchronized void removeListener(SetChangeListener<? super Item> listener) {
            items.removeListener(listener);
        }
        
        
    }
}
