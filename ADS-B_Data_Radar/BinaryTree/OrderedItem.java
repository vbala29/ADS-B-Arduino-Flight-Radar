//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala.binarytree;

import com.vikrambala.Airplane;

public abstract class OrderedItem {
    protected Airplane rightLink = null;
    protected Airplane leftLink = null;
    protected Object value;

    public abstract Airplane next();

    public abstract Airplane setNext(Airplane plane);

    public abstract Airplane previous();

    public abstract Airplane setPrevious(Airplane plane);

    public abstract int compareTo(Airplane plane);

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
