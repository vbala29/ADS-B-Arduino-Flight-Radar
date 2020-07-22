//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

import com.vikrambala.binarytree.NodeList;

import java.util.LinkedList;
import java.util.List;

public class SearchTree implements NodeList {
    private Airplane root = null;

    public Airplane getRoot() {
        return this.root;
    }

    public boolean addItem(Airplane plane) {
        if (this.root == null) {
            this.root = plane;
            return true;
        } else {
            Airplane currentItem = this.root;

            while(currentItem != null) {
                int comparison = currentItem.compareTo(plane);
                if (comparison == -1) {
                    if (currentItem.next() == null) {
                        currentItem.setNext(plane);
                        return true;
                    }

                    currentItem = currentItem.next();
                } else {

                    if (currentItem.previous() == null) {
                        currentItem.setPrevious(plane);
                        return true;
                    }

                    currentItem = currentItem.previous();
                }
            }
            return false;
        }
    }

    LinkedList<Airplane> orderedAircraft = new LinkedList<>(); // has to be outside otherwise every traverse call will create this
    //list and return it, leading to only the last entry ebing returned.
    public LinkedList<Airplane> traverse(Airplane root) {
        if (root != null) {
            this.traverse(root.previous());
            orderedAircraft.add(root);
            this.traverse(root.next());
        }
        return orderedAircraft;
    }
}
