//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala.binarytree;

import com.vikrambala.Airplane;

import java.util.LinkedList;
import java.util.List;

public interface NodeList {
    Airplane getRoot();

    boolean addItem(Airplane plane);

    LinkedList<Airplane> traverse(Airplane root);
}
