/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list.model;

import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**

 @author Hiram K <hiram.kamau@outlook.com>
 */
public class Item implements Comparable, Cloneable {

    private final ReadOnlyIntegerWrapper idProperty;
    private final SimpleStringProperty descriptionProperty;
    private final SimpleObjectProperty<Unit> unitProperty;
    private final SimpleObjectProperty<BigDecimal> quantityProperty;
    private final SimpleObjectProperty<BigDecimal> eachProperty;
    private final ObjectBinding<BigDecimal> totalBinding;

    public Item(
            int id,
            String description,
            Unit unit,
            BigDecimal quantity,
            BigDecimal each) {
        idProperty = new ReadOnlyIntegerWrapper(this, "id", id);
        descriptionProperty = new SimpleStringProperty(this, "description", description);
        unitProperty = new SimpleObjectProperty<>(this, "unit", unit);
        quantityProperty = new SimpleObjectProperty<>(this, "quantity", quantity);
        eachProperty = new SimpleObjectProperty<>(this, "each", each);
        totalBinding = Bindings.createObjectBinding(() -> {
            BigDecimal perUnit = eachProperty.get();
            BigDecimal qty = quantityProperty.get();
            return perUnit.multiply(qty);
        }, eachProperty, quantityProperty);
    }

    public ReadOnlyIntegerProperty idProperty() {
        return idProperty.getReadOnlyProperty();
    }

    public SimpleStringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public SimpleObjectProperty<Unit> unitProperty() {
        return unitProperty;
    }

    public SimpleObjectProperty<BigDecimal> quantityProperty() {
        return quantityProperty;
    }

    public SimpleObjectProperty<BigDecimal> eachProperty() {
        return eachProperty;
    }

    public ObjectBinding<BigDecimal> totalProperty() {
        return totalBinding;
    }

    public int getId() {
        return idProperty.get();
    }

    public String getDescription() {
        return descriptionProperty.get();
    }

    public void setDescription(String description) {
        descriptionProperty.set(description);
    }

    public Unit getUnit() {
        return unitProperty.get();
    }

    public void setUnit(Unit unit) {
        unitProperty.set(unit);
    }

    public BigDecimal getQuantity() {
        return quantityProperty.get();
    }

    public void setQuantity(BigDecimal quantity) {
        quantityProperty.set(quantity);
    }

    public BigDecimal getEach() {
        return eachProperty.get();
    }

    public void setEach(BigDecimal newValue) {
        eachProperty.set(newValue);
    }

    public final BigDecimal getTotal() {
        return totalBinding.get();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.getId());
        hash = 71 * hash + Objects.hashCode(this.getDescription());
        hash = 71 * hash + Objects.hashCode(this.getUnit());
        hash = 71 * hash + Objects.hashCode(this.getQuantity());
        hash = 71 * hash + Objects.hashCode(this.getEach());
        hash = 71 * hash + Objects.hashCode(this.getTotal());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(this.getDescription(), other.getDescription())) {
            return false;
        }
        if (!Objects.equals(this.getUnit(), other.getUnit())) {
            return false;
        }
        if (!Objects.equals(this.getQuantity(), other.getQuantity())) {
            return false;
        }
        if (!Objects.equals(this.getEach(), other.getEach())) {
            return false;
        }
        return Objects.equals(this.getTotal(), other.getTotal());
    }

    @Override
    public String toString() {
        return "Item{"
                + "id =" + getId()
                + ", description =" + getDescription()
                + ", unit =" + getUnit()
                + ", quantity =" + getQuantity()
                + ", @ =" + getEach()
                + ", total =" + getTotal() + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Item(getId(), getDescription(), getUnit(), getQuantity(), getEach());
    }

    @Override
    public int compareTo(Object o) {
        Integer thisId = getId();
        Integer otherId = ((Item) o).getId();
        return thisId.compareTo(otherId);
    }

    public enum Unit {
        PC, KG, L;
    }

}
